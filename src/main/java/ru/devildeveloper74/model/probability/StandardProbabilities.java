package ru.devildeveloper74.model.probability;

import java.util.Map;

public class StandardProbabilities {

    private int column;
    private int row;
    private Map<String, Integer> symbols;  // e.g., {"A": 1, "B": 2}

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

    public Map<String, Integer> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, Integer> symbols) {
        this.symbols = symbols;
    }
}

