package ru.devildeveloper74.model.probability;

import java.util.List;

public class Probabilities {
    private List<StandardProbability> standardSymbols;
    private BonusProbability bonusSymbols;

    public Probabilities(List<StandardProbability> standardSymbols, BonusProbability bonusSymbols) {
        this.standardSymbols = standardSymbols;
        this.bonusSymbols = bonusSymbols;
    }

    public List<StandardProbability> getStandardSymbols() {
        return standardSymbols;
    }

    public void setStandardSymbols(List<StandardProbability> standardSymbols) {
        this.standardSymbols = standardSymbols;
    }

    public BonusProbability getBonusSymbols() {
        return bonusSymbols;
    }

    public void setBonusSymbols(BonusProbability bonusSymbols) {
        this.bonusSymbols = bonusSymbols;
    }
}
