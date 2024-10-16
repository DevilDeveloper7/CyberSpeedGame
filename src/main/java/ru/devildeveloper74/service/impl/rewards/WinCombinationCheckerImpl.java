package ru.devildeveloper74.service.impl.rewards;

import ru.devildeveloper74.config.GameConfig;
import ru.devildeveloper74.model.WinCombination;
import ru.devildeveloper74.model.symbol.Symbol;
import ru.devildeveloper74.model.symbol.SymbolEntry;
import ru.devildeveloper74.model.symbol.SymbolWinCombination;
import ru.devildeveloper74.service.MatrixStatisticCollector;
import ru.devildeveloper74.service.WinCombinationChecker;
import ru.devildeveloper74.service.impl.matrix.MatrixStatisticCollectorImpl;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class WinCombinationCheckerImpl implements WinCombinationChecker {
    private final GameConfig gameConfig;
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    public WinCombinationCheckerImpl(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    @Override
    public void checkWinningPatterns(SymbolEntry[][] matrix) throws ExecutionException, InterruptedException {
        MatrixStatisticCollector statisticCollector = MatrixStatisticCollectorImpl.getInstance();
        Map<String, List<WinCombination>> groupedCombinations = gameConfig.groupWinCombinations();
        List<Callable<Set<SymbolWinCombination>>> tasks = new ArrayList<>();
        Set<SymbolWinCombination> symbolWinCombinations = new HashSet<>();

        // Process each group of win combinations in parallel
        for (Map.Entry<String, List<WinCombination>> entry : groupedCombinations.entrySet()) {
            List<WinCombination> groupCombinations = entry.getValue();
            tasks.add(() -> processGroup(groupCombinations, matrix));
        }

        // Execute all tasks in parallel
        List<Future<Set<SymbolWinCombination>>> results = executorService.invokeAll(tasks);

        for (Future<Set<SymbolWinCombination>> result : results) {
            try {
                Set<SymbolWinCombination> combinations = result.get();
                symbolWinCombinations.addAll(combinations);
            } catch (ExecutionException e) {
                System.err.println("Error in task: " + e.getCause().getMessage());
                throw new ExecutionException("Error processing tasks", e.getCause());
            }
        }

        Map<String, WinCombination> largestCombinations = symbolWinCombinations.stream()
                .collect(Collectors.toMap(
                        symbolWinCombination -> symbolWinCombination.winCombination().getWhen(),
                        SymbolWinCombination::winCombination,
                        (existingCombination, newCombination) ->
                                // Keep the one with the largest 'count'
                                newCombination.getRewardMultiplier() > existingCombination.getRewardMultiplier() ? newCombination : existingCombination
                ));

        // Now record only the largest combinations in the statisticCollector
        largestCombinations.forEach((when, winCombination) -> {
            Symbol symbol = symbolWinCombinations.parallelStream()
                    .filter(sw -> sw.winCombination().equals(winCombination))
                    .findFirst()
                    .orElseThrow()
                    .symbol();

            statisticCollector.recordWinCombinationApplied(symbol, Collections.singleton(winCombination));
        });
    }

    private Set<SymbolWinCombination> processGroup(List<WinCombination> groupCombinations, SymbolEntry[][] matrix) {
        Set<SymbolWinCombination> winCombinations = new HashSet<>();

        for (WinCombination winCombination : groupCombinations) {
            if (winCombination.getCoveredAreas() == null || winCombination.getCoveredAreas().isEmpty()) {
                // If there are no covered areas, check for duplicate symbols in the matrix
                Set<SymbolWinCombination> matchingCombinations = checkDuplicatesInMatrix(matrix, winCombination);

                // Record the combinations in the matrix collector
                winCombinations.addAll(matchingCombinations);
            } else {
                // Otherwise, check for winning patterns based on the covered areas
                SymbolWinCombination matchingAnyPattern = checkPattern(matrix, winCombination);
                if (matchingAnyPattern != null) {
                    winCombinations.add(matchingAnyPattern);
                }
            }
        }

        return winCombinations;
    }

    // Method to check for duplicate symbols in the matrix
    private Set<SymbolWinCombination> checkDuplicatesInMatrix(SymbolEntry[][] matrix, WinCombination winCombination) {
        MatrixStatisticCollector matrixStatisticCollector = MatrixStatisticCollectorImpl.getInstance();
        Set<SymbolWinCombination> matchingCombinations = new HashSet<>();

        // Check for matching combinations based on symbol appearing count
        for (SymbolEntry[] row : matrix) {
            for (SymbolEntry entry : row) {
                Symbol symbol = entry.symbol();
                int symbolAppearingCount = matrixStatisticCollector.getSymbolEntryCount(entry);
                if (symbolAppearingCount >= winCombination.getCount()) {
                    matchingCombinations.add(new SymbolWinCombination(symbol, winCombination));
                }
            }
        }
        return matchingCombinations;
    }

    private SymbolWinCombination checkPattern(SymbolEntry[][] matrix, WinCombination winCombination) {
        List<List<String>> coveredAreas = winCombination.getCoveredAreas();
        if (coveredAreas != null && !coveredAreas.isEmpty()) {
            for (List<String> area : coveredAreas) {
                Set<Symbol> symbolsInArea = new HashSet<>();
                Symbol winningSymbol = null; // Variable to store the winning symbol
                for (String coordinate : area) {
                    String[] parts = coordinate.split(":");
                    int row = Integer.parseInt(parts[0]);
                    int col = Integer.parseInt(parts[1]);
                    Symbol currentSymbol = matrix[row][col].symbol();
                    symbolsInArea.add(currentSymbol);

                    // Store the first symbol as potential winning symbol
                    if (winningSymbol == null) {
                        winningSymbol = currentSymbol;
                    }
                }
                // If all symbols are the same, return the winning symbol and the combination
                if (symbolsInArea.size() == 1) {
                    return new SymbolWinCombination(winningSymbol, winCombination);
                }
            }
        }
        return null; // Return null if no winning combination found
    }
}
