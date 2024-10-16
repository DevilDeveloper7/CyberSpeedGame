package ru.devildeveloper74.service;

import ru.devildeveloper74.model.WinCombination;
import ru.devildeveloper74.model.symbol.Symbol;
import ru.devildeveloper74.model.symbol.SymbolEntry;

import java.util.Map;
import java.util.Set;

public interface MatrixStatisticCollector {
    void recordSymbolEntry(SymbolEntry entry);

    int getSymbolEntryCount(SymbolEntry entry);

    Map<SymbolEntry, Integer> getSymbolEntryCountMap();

    void recordWinCombinationApplied(Symbol symbol, Set<WinCombination> combinations);

    Map<Symbol, Set<WinCombination>> getWinCombinationAppliedMap();

    Symbol getBonusAppliedForGame();

    void setBonusAppliedForGame(Symbol symbol);
}
