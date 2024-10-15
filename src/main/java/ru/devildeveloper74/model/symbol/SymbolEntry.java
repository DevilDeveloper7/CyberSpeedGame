package ru.devildeveloper74.model.symbol;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SymbolEntry that = (SymbolEntry) o;
        return Objects.equals(symbol, that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(symbol);
    }
}
