package ru.devildeveloper74.model.probability;

import ru.devildeveloper74.model.symbol.Symbol;

import java.util.Map;

public class StandardProbability {
    private int column;
    private int row;
    private Map<Symbol, Integer> symbolProbabilityMap;

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Map<Symbol, Integer> getSymbolProbabilityMap() {
        return symbolProbabilityMap;
    }

    public void setSymbolProbabilityMap(Map<Symbol, Integer> symbolProbabilityMap) {
        this.symbolProbabilityMap = symbolProbabilityMap;
    }
}

