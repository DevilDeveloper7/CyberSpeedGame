package ru.devildeveloper74.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.devildeveloper74.enums.BonusImpact;
import ru.devildeveloper74.model.symbol.BonusSymbol;
import ru.devildeveloper74.model.symbol.StandardSymbol;
import ru.devildeveloper74.model.symbol.Symbol;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SymbolDeserializer extends JsonDeserializer<Set<Symbol>> {

    @Override
    public Set<Symbol> deserialize(JsonParser jsonParser, DeserializationContext ctxt)
            throws IOException {

        Set<Symbol> symbols = new HashSet<>();
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode root = mapper.readTree(jsonParser);


        Iterator<Map.Entry<String, JsonNode>> fields = root.fields();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String symbolName = entry.getKey();
            JsonNode symbolData = entry.getValue();

            String type = symbolData.get("type").asText();
            JsonNode rewardMultiplierPath = symbolData.get("reward_multiplier");

            Double rewardMultiplier;

            if (rewardMultiplierPath != null) {
                rewardMultiplier = rewardMultiplierPath.asDouble();
            } else {
                rewardMultiplier = null;
            }

            if ("standard".equalsIgnoreCase(type)) {
                symbols.add(new StandardSymbol(symbolName, rewardMultiplier));
            } else if ("bonus".equalsIgnoreCase(type)) {
                BonusImpact impact = BonusImpact.valueOf(symbolData.get("impact").asText().toUpperCase());
                int extra = symbolData.has("extra") ? symbolData.get("extra").asInt() : 0;
                symbols.add(new BonusSymbol(symbolName, rewardMultiplier, impact, extra));
            } else {
                throw new IllegalArgumentException("Unknown symbol type: " + type);
            }
        }

        return symbols;
    }
}
