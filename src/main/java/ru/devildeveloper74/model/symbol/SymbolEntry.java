package ru.devildeveloper74.model.symbol;

public record SymbolEntry(Symbol symbol, int row, int column) {

    @Override
    public String toString() {
        return this.symbol.name;
    }

    @Override
    public Symbol symbol() {
        return symbol;
    }

    @Override
    public int row() {
        return row;
    }

    @Override
    public int column() {
        return column;
    }
}
