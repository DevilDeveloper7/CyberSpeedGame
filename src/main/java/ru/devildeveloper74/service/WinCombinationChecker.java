package ru.devildeveloper74.service;

import ru.devildeveloper74.model.symbol.SymbolEntry;

import java.util.concurrent.ExecutionException;

public interface WinCombinationChecker {
    void checkWinningPatterns(SymbolEntry[][] matrix) throws ExecutionException, InterruptedException;
}
