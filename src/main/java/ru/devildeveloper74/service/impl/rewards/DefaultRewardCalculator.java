package ru.devildeveloper74.service.impl.rewards;

import ru.devildeveloper74.config.GameConfig;
import ru.devildeveloper74.model.symbol.BonusSymbol;
import ru.devildeveloper74.model.symbol.Symbol;
import ru.devildeveloper74.service.RewardCalculator;

public class DefaultRewardCalculator implements RewardCalculator {
    private final GameConfig gameConfig;

    public DefaultRewardCalculator(GameConfig config) {
        this.gameConfig = config;
    }


    @Override
    public int calculate(String[][] matrix, int betAmount) {
        int totalReward = 0;
        // Logic to calculate reward based on matrix and config
        return totalReward;
    }

    @Override
    public double applyBonus(Symbol symbol, double reward) {
        if (symbol instanceof BonusSymbol bonusSymbol) {
            switch (bonusSymbol.getImpact()) {
                case MULTIPLY_REWARD:
                    reward *= bonusSymbol.getRewardMultiplier();
                    break;
                case EXTRA_BONUS:
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
