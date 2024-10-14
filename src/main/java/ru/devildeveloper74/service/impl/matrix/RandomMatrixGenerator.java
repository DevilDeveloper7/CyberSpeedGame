package ru.devildeveloper74.service.impl.matrix;

import ru.devildeveloper74.config.GameConfig;
import ru.devildeveloper74.service.MatrixGenerator;

public class RandomMatrixGenerator implements MatrixGenerator {
    private final GameConfig gameConfig;

    public RandomMatrixGenerator(GameConfig config) {
        this.gameConfig = config;
    }

    @Override
    public String[][] generateMatrix() {
        String[][] matrix = new String[gameConfig.getRows()][gameConfig.getColumns()];
        // Logic to fill matrix based on probabilities
        return matrix;
    }
}
