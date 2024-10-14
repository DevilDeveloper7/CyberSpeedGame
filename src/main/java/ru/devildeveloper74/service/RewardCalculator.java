package ru.devildeveloper74.service;

import ru.devildeveloper74.model.symbol.Symbol;

public interface RewardCalculator {
    int calculate(String[][] matrix, int betAmount);

    double applyBonus(Symbol symbol, double reward);
}
