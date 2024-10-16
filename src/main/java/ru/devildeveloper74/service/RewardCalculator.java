package ru.devildeveloper74.service;

import ru.devildeveloper74.model.symbol.Symbol;
import ru.devildeveloper74.model.symbol.SymbolEntry;

import java.util.concurrent.ExecutionException;

public interface RewardCalculator {
    int calculate(SymbolEntry[][] matrix, int betAmount) throws ExecutionException, InterruptedException;

    double applyBonus(Symbol symbol, double reward);
}
