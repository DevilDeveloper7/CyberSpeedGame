package ru.devildeveloper74.service.impl.matrix;

import ru.devildeveloper74.config.GameConfig;
import ru.devildeveloper74.model.probability.BonusProbability;
import ru.devildeveloper74.model.probability.StandardProbability;
import ru.devildeveloper74.model.symbol.BonusSymbol;
import ru.devildeveloper74.model.symbol.Symbol;
import ru.devildeveloper74.model.symbol.SymbolEntry;
import ru.devildeveloper74.service.MatrixGenerator;
import ru.devildeveloper74.service.MatrixStatisticCollector;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomMatrixGenerator implements MatrixGenerator {
    private final GameConfig gameConfig;

    public RandomMatrixGenerator(GameConfig config) {
        this.gameConfig = config;
    }

    @Override
    public SymbolEntry[][] generateMatrix() {
        SymbolEntry[][] matrix = new SymbolEntry[gameConfig.getRows()][gameConfig.getColumns()];
        MatrixStatisticCollector statisticsCollector = MatrixStatisticCollectorImpl.getInstance();

        List<StandardProbability> standardProbabilities = gameConfig.getProbabilities().getStandardSymbols();
        BonusProbability bonusProbability = gameConfig.getProbabilities().getBonusSymbols();

        // Get default probabilities from the first cell [0][0]
        StandardProbability uniformStandard = standardProbabilities.getFirst();
        int totalBonusProbability = calculateTotalProbability(bonusProbability.getSymbolProbabilityMap());
        int totalStandardProbability = calculateTotalProbability(uniformStandard.getSymbolProbabilityMap());

        boolean bonusGenerated = false;

        // Case 1: Multiple StandardProbability objects, specific to each cell
        if (standardProbabilities.size() > 1) {
            for (StandardProbability standard : standardProbabilities) {
                int row = standard.getRow();
                int column = standard.getColumn();

                // Calculate total probabilities for this cell
                totalStandardProbability = calculateTotalProbability(standard.getSymbolProbabilityMap());

                // Select a symbol for this specific cell
                Symbol selectedSymbol = selectSymbolBasedOnCategory(
                        standard,
                        bonusProbability,
                        totalStandardProbability,
                        totalBonusProbability,
                        bonusGenerated
                );
                if (selectedSymbol instanceof BonusSymbol) {
                    bonusGenerated = true;
                }

                SymbolEntry entry = new SymbolEntry(selectedSymbol, row, column);
                matrix[row][column] = entry;
                statisticsCollector.recordSymbolEntry(entry);
            }
        }
        // Case 2: Single StandardProbability object, uniform probabilities for all cells
        else if (standardProbabilities.size() == 1) {

            // Fill the matrix uniformly with random symbols based on the uniform probabilities
            for (int row = 0; row < gameConfig.getRows(); row++) {
                for (int column = 0; column < gameConfig.getColumns(); column++) {
                    Symbol selectedSymbol = selectSymbolBasedOnCategory(
                            uniformStandard,
                            bonusProbability,
                            totalStandardProbability,
                            totalBonusProbability,
                            bonusGenerated
                    );
                    if (selectedSymbol instanceof BonusSymbol) {
                        bonusGenerated = true;
                    }

                    SymbolEntry entry = new SymbolEntry(selectedSymbol, row, column);
                    matrix[row][column] = entry;
                    statisticsCollector.recordSymbolEntry(entry);
                }
            }
        }
        // Case 3: No specific standard probabilities, default to [0][0] for any additional cells
        for (int row = 0; row < gameConfig.getRows(); row++) {
            for (int column = 0; column < gameConfig.getColumns(); column++) {
                if (matrix[row][column] == null) {

                    Symbol selectedSymbol = selectSymbolBasedOnCategory(
                            uniformStandard,
                            bonusProbability,
                            totalStandardProbability,
                            totalBonusProbability,
                            bonusGenerated
                    );
                    if (selectedSymbol instanceof BonusSymbol) {
                        bonusGenerated = true;
                    }

                    SymbolEntry entry = new SymbolEntry(selectedSymbol, row, column);
                    matrix[row][column] = entry;
                    statisticsCollector.recordSymbolEntry(entry);
                }
            }
        }
        return matrix;
    }

    // Select a symbol based on the category (standard or bonus)
    private Symbol selectSymbolBasedOnCategory(
            StandardProbability standard,
            BonusProbability bonus,
            int totalStandardProbability,
            int totalBonusProbability,
            boolean bonusGenerated
    ) {
        int totalCombinedProbability = totalStandardProbability + (bonusGenerated ? 0 : totalBonusProbability);
        int randomValue = new Random().nextInt(totalCombinedProbability) + 1;

        // Decide if the symbol will be from standard or bonus category
        if (!bonusGenerated && randomValue > totalStandardProbability) {
            // Introduce a chance to skip the bonus symbol, allowing it to appear only sometimes
            int bonusChance = new Random().nextInt(100);
            if (bonusChance < 10) { // e.g., 10% chance to generate the bonus symbol
                randomValue -= totalStandardProbability;
                return selectSymbolFromMap(bonus.getSymbolProbabilityMap(), randomValue);
            }
        }
        // Otherwise, select a symbol from standard
        return selectSymbolFromMap(standard.getSymbolProbabilityMap(), randomValue);
    }

    // Calculate total probability for a symbol map
    private int calculateTotalProbability(Map<Symbol, Integer> symbolProbabilityMap) {
        return symbolProbabilityMap.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    // Helper function to select a symbol from a probability map
    private Symbol selectSymbolFromMap(Map<Symbol, Integer> symbolProbabilityMap, int randomValue) {
        for (Map.Entry<Symbol, Integer> entry : symbolProbabilityMap.entrySet()) {
            Symbol symbol = entry.getKey();
            int symbolProbability = entry.getValue();

            if (randomValue <= symbolProbability) {
                return symbol;
            }
            randomValue -= symbolProbability;
        }
        // Random symbol selected if with probability selected nothing
        return symbolProbabilityMap.entrySet().stream().findAny().get().getKey();
    }

    @Override
    public void printMatrix(SymbolEntry[][] matrix) {
        System.out.println("Matrix Symbol Entries:");
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[row].length; column++) {
                SymbolEntry entry = matrix[row][column];
                if (entry != null) {
                    System.out.printf("Row: %d, Column: %d, Symbol: %s%n", entry.row(), entry.column(), entry.toString());
                } else {
                    System.out.printf("Row: %d, Column: %d, Symbol: null%n", row, column);
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                SymbolEntry entry = matrix[i][j];
                System.out.print(entry.toString() + " ");
            }
            System.out.println();
        }
    }
}




