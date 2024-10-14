package ru.devildeveloper74.model;

import java.util.List;
import java.util.Map;

public record GameResult(String[][] matrix, int reward, Map<String, List<String>> appliedWinningCombinations,
                         String appliedBonusSymbol) {
}
