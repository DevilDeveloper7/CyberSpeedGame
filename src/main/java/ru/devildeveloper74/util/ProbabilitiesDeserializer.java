package ru.devildeveloper74.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.devildeveloper74.model.probability.BonusProbability;
import ru.devildeveloper74.model.probability.Probabilities;
import ru.devildeveloper74.model.probability.StandardProbability;
import ru.devildeveloper74.model.symbol.Symbol;

import java.io.IOException;
import java.util.*;

public class ProbabilitiesDeserializer extends JsonDeserializer<Probabilities> {

    private final Set<Symbol> symbolRegistry;

    public ProbabilitiesDeserializer(Set<Symbol> symbolRegistry) {
        this.symbolRegistry = symbolRegistry;  // Registry from previously mapped symbols
    }

    @Override
    public Probabilities deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode root = codec.readTree(jsonParser);

        JsonNode standardProbabilityNode = root.path("standard_symbols");
        JsonNode bonusProbabilityNode = root.path("bonus_symbols");

        List<StandardProbability> standardProbabilities = new ArrayList<>();

        // Iterate over the elements of the array using .elements()
        Iterator<JsonNode> standardSymbols = standardProbabilityNode.elements();

        while (standardSymbols.hasNext()) {
            JsonNode standard = standardSymbols.next();  // Get the current element in the array
            StandardProbability standardProbability = new StandardProbability();

            // Extract column and row for the current standard node
            standardProbability.setColumn(standard.get("column").asInt());
            standardProbability.setRow(standard.get("row").asInt());

            Map<Symbol, Integer> symbolsProbabilityMap = parseSymbols(standard.get("symbols"));
            standardProbability.setSymbolProbabilityMap(symbolsProbabilityMap);

            standardProbabilities.add(standardProbability);
        }

        // Parse the symbols for bonus node
        Map<Symbol, Integer> bonusSymbolsProbabilityMap = parseSymbols(bonusProbabilityNode.get("symbols"));

        BonusProbability bonusProbability = new BonusProbability();
        bonusProbability.setSymbolProbabilityMap(bonusSymbolsProbabilityMap);

        return new Probabilities(standardProbabilities, bonusProbability);
    }


    private Map<Symbol, Integer> parseSymbols(JsonNode symbolsNode) {
        Map<Symbol, Integer> symbolsProbabilityMap = new HashMap<>();
        Iterator<Map.Entry<String, JsonNode>> fields = symbolsNode.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String symbolName = entry.getKey();
            int probability = entry.getValue().asInt();

            // Lookup the Symbol in the registry
            Optional<Symbol> symbol = symbolRegistry.stream()
                    .filter(existingSymbol -> Objects.equals(existingSymbol.getName(), symbolName))
                    .findFirst();

            symbol.ifPresent(
                    present -> symbolsProbabilityMap.put(present, probability)
            );
        }

        return symbolsProbabilityMap;
    }
}
