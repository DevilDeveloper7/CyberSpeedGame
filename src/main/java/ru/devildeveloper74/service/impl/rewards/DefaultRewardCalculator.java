package ru.devildeveloper74.service.impl.rewards;

import ru.devildeveloper74.config.GameConfig;
import ru.devildeveloper74.model.WinCombination;
import ru.devildeveloper74.model.symbol.BonusSymbol;
import ru.devildeveloper74.model.symbol.Symbol;
import ru.devildeveloper74.model.symbol.SymbolEntry;
import ru.devildeveloper74.service.MatrixStatisticCollector;
import ru.devildeveloper74.service.RewardCalculator;
import ru.devildeveloper74.service.WinCombinationChecker;
import ru.devildeveloper74.service.impl.matrix.MatrixStatisticCollectorImpl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class DefaultRewardCalculator implements RewardCalculator {
    private final GameConfig gameConfig;

    public DefaultRewardCalculator(GameConfig config) {
        this.gameConfig = config;
    }

    @Override
    public int calculate(SymbolEntry[][] matrix, int betAmount) throws ExecutionException, InterruptedException {
        double totalReward = 0.0;
        //if matrix has a bonus symbol it should be stored for latest calculation of adding bonus reward
        BonusSymbol bonusSymbol = null;
        MatrixStatisticCollector matrixStatisticCollector = MatrixStatisticCollectorImpl.getInstance();
        WinCombinationChecker winCombinationChecker = new WinCombinationCheckerImpl(gameConfig);

        winCombinationChecker.checkWinningPatterns(matrix);

        boolean isWinGame = matrixStatisticCollector.getWinCombinationAppliedMap()
                .values()
                .stream()
                .anyMatch(s -> !s.isEmpty());

        if (!isWinGame) {
            return 0;
        }

        for (Map.Entry<Symbol, Set<WinCombination>> entry : matrixStatisticCollector.getWinCombinationAppliedMap().entrySet()) {
            Symbol symbol = entry.getKey();
            Set<WinCombination> winCombinations = entry.getValue();

            if (symbol instanceof BonusSymbol) {
                bonusSymbol = (BonusSymbol) symbol;
            }

            // If symbol has at least one matching win combination, apply the formula
            if (!winCombinations.isEmpty()) {
                double symbolReward = betAmount; // Start with the bet amount

                // Multiply for each matching combination
                for (WinCombination winCombination : winCombinations) {
                    symbolReward *= winCombination.getRewardMultiplier();
                }

                // Multiply by the basic symbol reward multiplier
                symbolReward *= symbol.getRewardMultiplier();

                // Add symbol reward to the total reward
                totalReward += symbolReward;
            }
        }

        if (bonusSymbol != null) {
            totalReward = applyBonus(bonusSymbol, totalReward);
            matrixStatisticCollector.setBonusAppliedForGame(bonusSymbol);
        }

        return (int) totalReward;
    }

    @Override
    public double applyBonus(Symbol symbol, double reward) {
        if (symbol instanceof BonusSymbol bonusSymbol) {
            switch (bonusSymbol.getImpact()) {
                case MULTIPLY_REWARD:
                    // Multiply the reward based on the bonus symbol's multiplier
                    reward *= bonusSymbol.getRewardMultiplier();
                    break;
                case EXTRA_BONUS:
                    // Add the extra bonus amount to the reward
                    reward += bonusSymbol.getExtra();
                    break;
                case MISS:
                    // No impact on the reward
                    break;
            }
        }
        return reward;
    }
}
