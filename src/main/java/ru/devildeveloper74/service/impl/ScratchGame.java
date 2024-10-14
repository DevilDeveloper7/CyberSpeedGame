package ru.devildeveloper74.service.impl;

import ru.devildeveloper74.model.GameResult;
import ru.devildeveloper74.service.Game;
import ru.devildeveloper74.service.MatrixGenerator;
import ru.devildeveloper74.service.RewardCalculator;

public class ScratchGame implements Game {
    private final MatrixGenerator matrixGenerator;
    private final RewardCalculator rewardCalculator;

    public ScratchGame(MatrixGenerator matrixGenerator, RewardCalculator rewardCalculator) {
        this.matrixGenerator = matrixGenerator;
        this.rewardCalculator = rewardCalculator;
    }

    @Override
    public GameResult play(int betAmount) {
        String[][] matrix = matrixGenerator.generateMatrix();
        int reward = rewardCalculator.calculate(matrix, betAmount);
        return new GameResult(matrix, reward);
    }
}
