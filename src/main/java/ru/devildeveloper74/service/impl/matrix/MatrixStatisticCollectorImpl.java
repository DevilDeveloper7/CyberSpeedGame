package ru.devildeveloper74.service.impl.matrix;

import ru.devildeveloper74.model.symbol.SymbolEntry;
import ru.devildeveloper74.service.MatrixStatisticCollector;

import java.util.HashMap;
import java.util.Map;

public class MatrixStatisticCollectorImpl implements MatrixStatisticCollector {
    private static volatile MatrixStatisticCollectorImpl instance;

    // Map for symbol entry counts
    private final Map<SymbolEntry, Integer> symbolEntryCountMap = new HashMap<>();

    private MatrixStatisticCollectorImpl() {}

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
}
