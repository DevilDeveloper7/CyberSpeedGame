package ru.devildeveloper74.model.symbol;

import ru.devildeveloper74.enums.SymbolType;

import java.util.Objects;

public abstract class Symbol {

    protected String name;
    protected SymbolType type;
    protected Double rewardMultiplier;

    public Symbol(String name, SymbolType type, Double rewardMultiplier) {
        this.name = name;
        this.type = type;
        this.rewardMultiplier = rewardMultiplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SymbolType getType() {
        return type;
    }

    public void setType(SymbolType type) {
        this.type = type;
    }

    public void setRewardMultiplier(Double rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }


    public double getRewardMultiplier() {
        return rewardMultiplier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        return Double.compare(rewardMultiplier, symbol.rewardMultiplier) == 0 && Objects.equals(name, symbol.name) && type == symbol.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, rewardMultiplier);
    }
}

