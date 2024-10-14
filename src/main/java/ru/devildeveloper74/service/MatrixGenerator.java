package ru.devildeveloper74.service;

import ru.devildeveloper74.model.symbol.SymbolEntry;

public interface MatrixGenerator {

    SymbolEntry[][] generateMatrix();

    void printMatrix(SymbolEntry[][] matrix);
}
