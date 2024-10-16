package ru.devildeveloper74.service.impl.matrix;

import ru.devildeveloper74.model.WinCombination;
import ru.devildeveloper74.model.symbol.Symbol;
import ru.devildeveloper74.model.symbol.SymbolEntry;
import ru.devildeveloper74.service.MatrixStatisticCollector;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class MatrixStatisticCollectorImpl implements MatrixStatisticCollector {
    private static volatile MatrixStatisticCollectorImpl instance;

    // Map for symbol entry counts
    private final Map<SymbolEntry, Integer> symbolEntryCountMap = new HashMap<>();

    //Storing winning combinations were applied for current game
    private final Map<Symbol, Set<WinCombination>> appliedWinningCombinations = new ConcurrentHashMap<>();

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
    public void recordWinCombinationApplied(Symbol symbol, Set<WinCombination> combinations) {
        appliedWinningCombinations.compute(symbol, (key, existingCombinations) -> {
            if (existingCombinations == null) {
                existingCombinations = new CopyOnWriteArraySet<>(combinations);
            } else {
                existingCombinations.addAll(combinations);
            }
            return existingCombinations;
        });
    }

    @Override
    public Map<Symbol, Set<WinCombination>> getWinCombinationAppliedMap() {
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
