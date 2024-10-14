package ru.devildeveloper74.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.devildeveloper74.model.WinCombinations;
import ru.devildeveloper74.model.probability.Probabilities;
import ru.devildeveloper74.model.symbol.Symbol;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class GameConfig {
    private int columns;
    private int rows;
    private Set<Symbol> symbols;
    private Probabilities probabilities;
    private Map<String, WinCombinations> winCombinations;


    public static GameConfig loadFromFile(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new SymbolModule());
        Set<Symbol> mySymbols = mapper.readValue(new File(filePath), new TypeReference<Set<Symbol>>() {
        });

        mapper.registerModule(new ProbabilitiesModule(mySymbols));

        Probabilities probabilities = mapper.readValue(new File(filePath), new TypeReference<Probabilities>() {
        });


//        mapper.registerModule(new ProbabilitiesModule(this.symbols));
        return mapper.readValue(new File(filePath), GameConfig.class);
    }

    // Getters and setters


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

    public Map<String, WinCombinations> getWinCombinations() {
        return winCombinations;
    }

    public void setWinCombinations(Map<String, WinCombinations> winCombinations) {
        this.winCombinations = winCombinations;
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
