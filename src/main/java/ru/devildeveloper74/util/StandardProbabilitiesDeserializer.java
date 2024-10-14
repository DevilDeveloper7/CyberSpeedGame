package ru.devildeveloper74.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.devildeveloper74.model.probability.StandardProbabilities;
import ru.devildeveloper74.model.symbol.Symbol;

import java.io.IOException;
import java.util.*;

public class StandardProbabilitiesDeserializer extends JsonDeserializer<StandardProbabilities> {

    private final Set<Symbol> symbolRegistry;

    public StandardProbabilitiesDeserializer(Set<Symbol> symbolRegistry) {
        this.symbolRegistry = symbolRegistry;  // Registry from previously mapped symbols
    }

    @Override
    public StandardProbabilities deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode root = codec.readTree(jsonParser);

        JsonNode probabilityNode = root.path("probabilities").path("standard_symbols");

        StandardProbabilities probabilities = new StandardProbabilities();
        probabilities.setColumn(probabilityNode.get("column").asInt());
        probabilities.setRow(probabilityNode.get("row").asInt());

        // Parse the symbols
        JsonNode symbolsNode = probabilityNode.get("symbols");
        Map<Symbol, Integer> symbols = new HashMap<>();
        Iterator<Map.Entry<String, JsonNode>> fields = symbolsNode.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String symbolName = entry.getKey();
            int probability = entry.getValue().asInt();

            // Lookup the Symbol in the registry
            Optional<Symbol> symbol = symbolRegistry.stream().filter(existingSymbol -> Objects.equals(existingSymbol.getName(), symbolName)).findFirst();

            symbol.ifPresent(
                    present -> symbols.put(present, probability)
            );
        }

//        probabilities.setSymbols(symbols);
        return probabilities;
    }
}
