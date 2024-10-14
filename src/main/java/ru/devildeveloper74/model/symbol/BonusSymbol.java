package ru.devildeveloper74.model.symbol;

import ru.devildeveloper74.enums.BonusImpact;

public class BonusSymbol extends Symbol {
    private final BonusImpact impact;
    private final int extra;

    public BonusSymbol(String name, Double rewardMultiplier, BonusImpact impact, int extra) {
        super(name, SymbolType.BONUS, rewardMultiplier);
        this.impact = impact;
        this.extra = extra;
    }

    @Override
    public double applyReward(int reward) {
        return 0;
    }

    public int getExtra() {
        return extra;
    }

    public BonusImpact getImpact() {
        return impact;
    }
}
