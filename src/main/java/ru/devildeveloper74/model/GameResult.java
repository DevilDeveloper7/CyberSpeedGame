package ru.devildeveloper74.model;

import java.util.Arrays;

public class GameResult {
    private final String[][] matrix;
    private final int reward;

    public GameResult(String[][] matrix, int reward) {
        this.matrix = matrix;
        this.reward = reward;
    }

    @Override
    public String toString() {
        return "GameResult{" +
                "matrix=" + Arrays.toString(matrix) +
                ", reward=" + reward +
                '}';
    }
}
