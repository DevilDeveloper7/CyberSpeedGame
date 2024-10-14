package ru.devildeveloper74.model.probability;

import ru.devildeveloper74.model.symbol.Symbol;

import java.util.Map;

public class BonusProbability {
    private Map<Symbol, Integer> symbolProbabilityMap;

    public Map<Symbol, Integer> getSymbolProbabilityMap() {
        return symbolProbabilityMap;
    }

    public void setSymbolProbabilityMap(Map<Symbol, Integer> symbolProbabilityMap) {
        this.symbolProbabilityMap = symbolProbabilityMap;
    }
}
