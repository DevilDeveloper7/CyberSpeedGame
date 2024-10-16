package ru.devildeveloper74.model;

import ru.devildeveloper74.model.symbol.Symbol;

import java.util.Map;
import java.util.Set;

public record GameResult(String[][] matrix, int reward, Map<Symbol, Set<WinCombination>> appliedWinningCombinations,
                         String appliedBonusSymbol) {
}
