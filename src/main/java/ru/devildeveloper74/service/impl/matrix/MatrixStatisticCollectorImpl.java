package ru.devildeveloper74.service.impl.matrix;

import ru.devildeveloper74.model.symbol.Symbol;
import ru.devildeveloper74.model.symbol.SymbolEntry;
import ru.devildeveloper74.service.MatrixStatisticCollector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatrixStatisticCollectorImpl implements MatrixStatisticCollector {
    private static volatile MatrixStatisticCollectorImpl instance;

    // Map for symbol entry counts
    private final Map<SymbolEntry, Integer> symbolEntryCountMap = new HashMap<>();

    //Storing winning combinations were applied for current game
    private final Map<String, List<String>> appliedWinningCombinations = new HashMap<>();

    //Storing bonus was applied for current game
    private Symbol bonus;

    private MatrixStatisticCollectorImpl() {
    }

    public static MatrixStatisticCollectorImpl getInstance() {
        if (instance == null) {
            synchronized (MatrixStatisticCollectorImpl.class) {
                if (instance == null) {
                    instance = new MatrixStatisticCollectorImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void recordSymbolEntry(SymbolEntry entry) {
        symbolEntryCountMap.merge(entry, 1, Integer::sum);
    }

    @Override
    public int getSymbolEntryCount(SymbolEntry entry) {
        return symbolEntryCountMap.getOrDefault(entry, 0);
    }

    @Override
    public Map<SymbolEntry, Integer> getSymbolEntryCountMap() {
        return symbolEntryCountMap;
    }

    @Override
    public void recordWinCombinationApplied(String symbol, List<String> combinations) {
        appliedWinningCombinations.merge(symbol, combinations, (existingCombinations, newCombinations) -> {
            existingCombinations.addAll(combinations);
            return newCombinations;
        });
    }

    @Override
    public Map<String, List<String>> getWinCombinationAppliedMap() {
        return appliedWinningCombinations;
    }

    @Override
    public Symbol getBonusAppliedForGame() {
        return bonus;
    }

    @Override
    public void setBonusAppliedForGame(Symbol symbol) {
        this.bonus = symbol;
    }
}
