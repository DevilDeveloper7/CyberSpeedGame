package ru.devildeveloper74.service;

import ru.devildeveloper74.model.symbol.SymbolEntry;

import java.util.Map;

public interface MatrixStatisticCollector {
    void recordSymbolEntry(SymbolEntry entry);

    int getSymbolEntryCount(SymbolEntry entry);

    Map<SymbolEntry, Integer> getSymbolEntryCountMap();
}
