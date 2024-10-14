package ru.devildeveloper74.model.probability;

import java.util.List;

public class Probabilities {
    private List<StandardProbabilities> standardSymbols;
    private BonusProbabilities bonusSymbols;

    public List<StandardProbabilities> getStandardSymbols() {
        return standardSymbols;
    }

    public void setStandardSymbols(List<StandardProbabilities> standardSymbols) {
        this.standardSymbols = standardSymbols;
    }

    public BonusProbabilities getBonusSymbols() {
        return bonusSymbols;
    }

    public void setBonusSymbols(BonusProbabilities bonusSymbols) {
        this.bonusSymbols = bonusSymbols;
    }
}
