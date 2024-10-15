package ru.devildeveloper74.service;

import ru.devildeveloper74.model.WinCombination;
import ru.devildeveloper74.model.symbol.Symbol;

import java.util.Set;

public interface WinCombinationChecker {
    Set<WinCombination> findMatchingCombinations(Symbol symbol, int count);
}
