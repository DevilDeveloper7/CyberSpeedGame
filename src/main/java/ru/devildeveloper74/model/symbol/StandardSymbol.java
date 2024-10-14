package ru.devildeveloper74.model.symbol;

public class StandardSymbol extends Symbol {
    public StandardSymbol(String name, Double rewardMultiplier) {
        super(name, SymbolType.STANDARD, rewardMultiplier);
    }

    @Override
    public double applyReward(int reward) {
        return 0;
    }


}
