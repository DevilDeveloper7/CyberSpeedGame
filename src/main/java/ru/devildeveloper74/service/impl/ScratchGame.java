package ru.devildeveloper74.service.impl;

import ru.devildeveloper74.model.GameResult;
import ru.devildeveloper74.model.symbol.SymbolEntry;
import ru.devildeveloper74.service.Game;
import ru.devildeveloper74.service.MatrixGenerator;
import ru.devildeveloper74.service.MatrixStatisticCollector;
import ru.devildeveloper74.service.RewardCalculator;
import ru.devildeveloper74.service.impl.matrix.MatrixStatisticCollectorImpl;

public class ScratchGame implements Game {
    private final MatrixGenerator matrixGenerator;
    private final RewardCalculator rewardCalculator;

    public ScratchGame(MatrixGenerator matrixGenerator, RewardCalculator rewardCalculator) {
        this.matrixGenerator = matrixGenerator;
        this.rewardCalculator = rewardCalculator;
    }

    @Override
    public GameResult play(int betAmount) {
        MatrixStatisticCollector statisticCollector = MatrixStatisticCollectorImpl.getInstance();
        SymbolEntry[][] matrix = matrixGenerator.generateMatrix();
        int reward = rewardCalculator.calculate(matrix, betAmount);
        String[][] outputMatrix = matrixGenerator.convertToStringMatrix(matrix);

        return new GameResult(
                outputMatrix,
                reward,
                statisticCollector.getWinCombinationAppliedMap(),
                statisticCollector.getBonusAppliedForGame() == null ? "no bonus has been applied" : statisticCollector.getBonusAppliedForGame().getName()
        );
    }
}
