package ru.devildeveloper74.service.impl.matrix;

import ru.devildeveloper74.config.GameConfig;
import ru.devildeveloper74.model.probability.BonusProbability;
import ru.devildeveloper74.model.probability.StandardProbability;
import ru.devildeveloper74.model.symbol.Symbol;
import ru.devildeveloper74.model.symbol.SymbolEntry;
import ru.devildeveloper74.service.MatrixGenerator;

import java.util.HashMap;
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

        List<StandardProbability> standardProbabilities = gameConfig.getProbabilities().getStandardSymbols();
        BonusProbability bonusProbability = gameConfig.getProbabilities().getBonusSymbols();

        // Get default probabilities from the first cell [0][0]
        Map<Symbol, Integer> defaultProbabilities = standardProbabilities.isEmpty() ?
                new HashMap<>() : standardProbabilities.getFirst().getSymbolProbabilityMap();

        // Case 1: Multiple StandardProbability objects, specific to each cell
        if (standardProbabilities.size() > 1) {
            for (StandardProbability standard : standardProbabilities) {
                int row = standard.getRow();
                int column = standard.getColumn();

                // Calculate total probabilities for this cell
                int totalStandardProbability = calculateTotalProbability(standard.getSymbolProbabilityMap());
                int totalBonusProbability = calculateTotalProbability(bonusProbability.getSymbolProbabilityMap());

                // Select a symbol for this specific cell
                Symbol selectedSymbol = selectSymbolBasedOnCategory(standard, bonusProbability, totalStandardProbability, totalBonusProbability);

                // Store the symbol and its coordinates in the matrix
                matrix[row][column] = new SymbolEntry(selectedSymbol, row, column);
            }
        }
        // Case 2: Single StandardProbability object, uniform probabilities for all cells
        else if (standardProbabilities.size() == 1) {
            StandardProbability uniformStandard = standardProbabilities.getFirst();

            // Calculate total probabilities for uniform distribution
            int totalStandardProbability = calculateTotalProbability(uniformStandard.getSymbolProbabilityMap());
            int totalBonusProbability = calculateTotalProbability(bonusProbability.getSymbolProbabilityMap());

            // Fill the matrix uniformly with random symbols based on the uniform probabilities
            for (int row = 0; row < gameConfig.getRows(); row++) {
                for (int column = 0; column < gameConfig.getColumns(); column++) {
                    Symbol selectedSymbol = selectSymbolBasedOnCategory(uniformStandard, bonusProbability, totalStandardProbability, totalBonusProbability);
                    matrix[row][column] = new SymbolEntry(selectedSymbol, row, column);
                }
            }
        }
        // Case 3: No specific standard probabilities, default to [0][0] for any additional cells
        for (int row = 0; row < gameConfig.getRows(); row++) {
            for (int column = 0; column < gameConfig.getColumns(); column++) {
                if (matrix[row][column] == null) { // Only fill if it's still null
                    // Use default probabilities from [0][0]
                    Symbol selectedSymbol = selectSymbolFromMap(defaultProbabilities, new Random().nextInt(calculateTotalProbability(defaultProbabilities)) + 1);
                    matrix[row][column] = new SymbolEntry(selectedSymbol, row, column);
                }
            }
        }

        return matrix;
    }

    // Select a symbol based on the category (standard or bonus)
    private Symbol selectSymbolBasedOnCategory(StandardProbability standard, BonusProbability bonus, int totalStandardProbability, int totalBonusProbability) {
        int totalCombinedProbability = totalStandardProbability + totalBonusProbability;
        int randomValue = new Random().nextInt(totalCombinedProbability) + 1;

        // Decide if the symbol will be from standard or bonus category
        if (randomValue <= totalStandardProbability) {
            // Select a symbol from standard
            return selectSymbolFromMap(standard.getSymbolProbabilityMap(), randomValue);
        } else {
            // Select a symbol from bonus
            randomValue -= totalStandardProbability; // Adjust randomValue for bonus selection
            return selectSymbolFromMap(bonus.getSymbolProbabilityMap(), randomValue);
        }
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
        return null; // No symbol selected
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
    }
}




