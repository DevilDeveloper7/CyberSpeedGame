package ru.devildeveloper74.model.symbol;

import ru.devildeveloper74.enums.SymbolType;

public class StandardSymbol extends Symbol {
    public StandardSymbol(String name, Double rewardMultiplier) {
        super(name, SymbolType.STANDARD, rewardMultiplier);
    }
}
