package ru.devildeveloper74.service;

import ru.devildeveloper74.model.symbol.Symbol;
import ru.devildeveloper74.model.symbol.SymbolEntry;

public interface RewardCalculator {
    int calculate(SymbolEntry[][] matrix, int betAmount);

    double applyBonus(Symbol symbol, double reward);
}
