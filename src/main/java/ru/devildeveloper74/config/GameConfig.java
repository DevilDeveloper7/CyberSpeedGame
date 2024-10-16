package ru.devildeveloper74.config;

import ru.devildeveloper74.model.WinCombination;
import ru.devildeveloper74.model.probability.Probabilities;
import ru.devildeveloper74.model.symbol.Symbol;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GameConfig {
    private int columns;
    private int rows;
    private Set<Symbol> symbols;
    private Probabilities probabilities;
    private List<WinCombination> winCombinations;

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public Set<Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(Set<Symbol> symbols) {
        this.symbols = symbols;
    }

    public Probabilities getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(Probabilities probabilities) {
        this.probabilities = probabilities;
    }

    public List<WinCombination> getWinCombinations() {
        return winCombinations;
    }

    public void setWinCombinations(List<WinCombination> winCombinations) {
        this.winCombinations = winCombinations;
    }

    public Map<String, List<WinCombination>> groupWinCombinations() {
        return winCombinations.stream()
                .collect(Collectors.groupingBy(WinCombination::getGroup));
    }

    @Override
    public String toString() {
        return "GameConfig{" +
                "columns=" + columns +
                ", rows=" + rows +
                ", symbols=" + symbols +
                ", probabilities=" + probabilities +
                ", winCombinations=" + winCombinations +
                '}';
    }
}
