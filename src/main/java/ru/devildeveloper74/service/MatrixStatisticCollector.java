package ru.devildeveloper74.service;

import ru.devildeveloper74.model.symbol.Symbol;
import ru.devildeveloper74.model.symbol.SymbolEntry;

import java.util.List;
import java.util.Map;

public interface MatrixStatisticCollector {
    void recordSymbolEntry(SymbolEntry entry);

    int getSymbolEntryCount(SymbolEntry entry);

    Map<SymbolEntry, Integer> getSymbolEntryCountMap();

    void recordWinCombinationApplied(String symbol, List<String> combinations);

    Map<String, List<String>> getWinCombinationAppliedMap();

    Symbol getBonusAppliedForGame();

    void setBonusAppliedForGame(Symbol symbol);
}
