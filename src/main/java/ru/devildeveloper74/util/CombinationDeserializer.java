package ru.devildeveloper74.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.devildeveloper74.model.WinCombination;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CombinationDeserializer extends JsonDeserializer<List<WinCombination>> {


    @Override
    public List<WinCombination> deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode root = codec.readTree(jsonParser);


        List<WinCombination> winCombinationsList = new ArrayList<>();

        // Iterate through the entries in the "win_combinations" object
        Iterator<Map.Entry<String, JsonNode>> combinationsIterator = root.fields();
        while (combinationsIterator.hasNext()) {
            Map.Entry<String, JsonNode> entry = combinationsIterator.next();
            String combinationName = entry.getKey();
            JsonNode combinationNode = entry.getValue();

            WinCombination winCombination = new WinCombination();

            JsonNode rewardMultiplierPath = combinationNode.get("reward_multiplier");
            JsonNode countPath = combinationNode.get("count");

            final Double rewardMultiplier;
            final Integer count;

            if (rewardMultiplierPath != null) {
                rewardMultiplier = rewardMultiplierPath.asDouble();
            } else {
                rewardMultiplier = null;
            }

            if (countPath != null) {
                count = countPath.asInt();
            } else {
                count = null;
            }

            // Map the fields of WinCombination
            winCombination.setName(combinationName);
            winCombination.setRewardMultiplier(rewardMultiplier);
            winCombination.setWhen(combinationNode.get("when").asText());
            winCombination.setCount(count);
            winCombination.setGroup(combinationNode.get("group").asText());

            JsonNode coveredAreasNode = combinationNode.get("covered_areas");
            if (coveredAreasNode != null && coveredAreasNode.isArray()) {
                List<List<String>> coveredAreas = new ArrayList<>();
                for (JsonNode areaNode : coveredAreasNode) {
                    List<String> areaList = new ArrayList<>();
                    areaNode.forEach(symbolNode -> areaList.add(symbolNode.asText()));
                    coveredAreas.add(areaList);
                }
                winCombination.setCoveredAreas(coveredAreas);
            } else {
                winCombination.setCoveredAreas(new ArrayList<>());
            }

            winCombinationsList.add(winCombination);
        }

        return winCombinationsList;
    }
}
