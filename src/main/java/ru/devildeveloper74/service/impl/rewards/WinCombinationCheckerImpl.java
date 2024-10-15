package ru.devildeveloper74.service.impl.rewards;

import ru.devildeveloper74.config.GameConfig;
import ru.devildeveloper74.model.WinCombination;
import ru.devildeveloper74.model.symbol.Symbol;
import ru.devildeveloper74.service.MatrixStatisticCollector;
import ru.devildeveloper74.service.WinCombinationChecker;
import ru.devildeveloper74.service.impl.matrix.MatrixStatisticCollectorImpl;

import java.util.*;

public class WinCombinationCheckerImpl implements WinCombinationChecker {
    private final GameConfig gameConfig;

    public WinCombinationCheckerImpl(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    // Helper method to find all winning combinations for a symbol that matches the count threshold
    public Set<WinCombination> findMatchingCombinations(Symbol symbol, int count) {
        MatrixStatisticCollector matrixStatisticCollector = MatrixStatisticCollectorImpl.getInstance();

        // Map to store the largest multiplier for each 'when' category
        Map<String, WinCombination> bestCombinations = new HashMap<>();

        for (WinCombination winCombination : gameConfig.getWinCombinations()) {
            if (winCombination.getCount() != null && count >= winCombination.getCount()) {
                String whenCategory = winCombination.getWhen();

                // If this 'when' category already has a combination, compare multipliers
                if (bestCombinations.containsKey(whenCategory)) {
                    WinCombination existingCombination = bestCombinations.get(whenCategory);

                    // Replace with the combination with the highest multiplier
                    if (winCombination.getRewardMultiplier() > existingCombination.getRewardMultiplier()) {
                        bestCombinations.put(whenCategory, winCombination);

                        //Updating the statistic of applied combinations to current matrix
                        matrixStatisticCollector.getWinCombinationAppliedMap().remove(symbol.getName());
                        matrixStatisticCollector.recordWinCombinationApplied(symbol.getName(), new ArrayList<String>() {{
                            add(winCombination.getName());
                        }});
                    }
                } else {
                    bestCombinations.put(whenCategory, winCombination);

                    matrixStatisticCollector.recordWinCombinationApplied(symbol.getName(), new ArrayList<String>() {{
                        add(winCombination.getName());
                    }});
                }
            }
        }
        return new HashSet<>(bestCombinations.values());
    }
}
