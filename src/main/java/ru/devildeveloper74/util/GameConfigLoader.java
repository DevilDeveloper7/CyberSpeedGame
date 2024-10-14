package ru.devildeveloper74.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.devildeveloper74.config.GameConfig;
import ru.devildeveloper74.config.json.ProbabilitiesModule;
import ru.devildeveloper74.config.json.SymbolModule;
import ru.devildeveloper74.config.json.WinCombinationModule;
import ru.devildeveloper74.model.WinCombination;
import ru.devildeveloper74.model.probability.Probabilities;
import ru.devildeveloper74.model.symbol.Symbol;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class GameConfigLoader {
    public static GameConfig loadFromFile(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new SymbolModule());
        mapper.registerModule(new WinCombinationModule());

        JsonNode rootNode = mapper.readTree(new File(filePath));

        // Extract the required parts from the JSON
        JsonNode symbolsNode = rootNode.get("symbols");
        Set<Symbol> mySymbols = mapper.convertValue(symbolsNode, new TypeReference<Set<Symbol>>() {
        });

        JsonNode winCombinationsNode = rootNode.get("win_combinations");
        List<WinCombination> winCombinations = mapper.convertValue(winCombinationsNode, new TypeReference<List<WinCombination>>() {
        });

        mapper.registerModule(new ProbabilitiesModule(mySymbols));
        JsonNode probabilitiesNode = rootNode.get("probabilities");
        Probabilities probabilities = mapper.convertValue(probabilitiesNode, new TypeReference<Probabilities>() {
        });

        // Create and populate the GameConfig object
        GameConfig gameConfig = new GameConfig();
        gameConfig.setColumns(rootNode.get("columns").asInt());
        gameConfig.setRows(rootNode.get("rows").asInt());
        gameConfig.setSymbols(mySymbols);
        gameConfig.setProbabilities(probabilities);
        gameConfig.setWinCombinations(winCombinations);

        return gameConfig;
    }
}
