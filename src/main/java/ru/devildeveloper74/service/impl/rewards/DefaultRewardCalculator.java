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

public class DefaultRewardCalculator implements RewardCalculator {
    private final GameConfig gameConfig;

    public DefaultRewardCalculator(GameConfig config) {
        this.gameConfig = config;
    }

    @Override
    public int calculate(SymbolEntry[][] matrix, int betAmount) {
        MatrixStatisticCollector matrixStatisticCollector = MatrixStatisticCollectorImpl.getInstance();
        WinCombinationChecker winCombinationChecker = new WinCombinationCheckerImpl(gameConfig);

        double totalReward = 0.0;
        boolean isWinGame = false;

        //if matrix has a bonus symbol it should be stored for latest calculation of adding bonus reward
        BonusSymbol bonusSymbol = null;

        for (Map.Entry<SymbolEntry, Integer> entry : matrixStatisticCollector.getSymbolEntryCountMap().entrySet()) {
            SymbolEntry symbolEntry = entry.getKey();
            int count = entry.getValue();

            if (symbolEntry.symbol() instanceof BonusSymbol) {
                bonusSymbol = (BonusSymbol) symbolEntry.symbol();
            }

            // Get winning combinations for the symbol if it meets the threshold count
            Set<WinCombination> matchingCombinations = winCombinationChecker.findMatchingCombinations(symbolEntry.symbol(), count);

            // If symbol has at least one matching win combination, apply the formula
            if (!matchingCombinations.isEmpty()) {
                double symbolReward = betAmount; // Start with the bet amount
                isWinGame = true;

                // Multiply for each matching combination
                for (WinCombination winCombination : matchingCombinations) {
                    symbolReward *= winCombination.getRewardMultiplier();
                }

                // Multiply by the basic symbol reward multiplier
                symbolReward *= symbolEntry.symbol().getRewardMultiplier();

                // Add symbol reward to the total reward
                totalReward += symbolReward;
            }
        }

        if (bonusSymbol != null && isWinGame) {
            totalReward = applyBonus(bonusSymbol, totalReward);
            matrixStatisticCollector.setBonusAppliedForGame(bonusSymbol);
        }

        if (!isWinGame) {
            return 0;
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
