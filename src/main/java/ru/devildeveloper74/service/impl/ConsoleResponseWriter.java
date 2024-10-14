package ru.devildeveloper74.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.devildeveloper74.model.GameResult;
import ru.devildeveloper74.service.ResponseWriter;

import java.io.IOException;
import java.util.Map;

public class ConsoleResponseWriter implements ResponseWriter {
    private final ObjectMapper objectMapper;

    public ConsoleResponseWriter() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public void writeResponse(GameResult result) {
        try {
            String response = objectMapper.writeValueAsString(formatResponse(result));
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Object> formatResponse(GameResult result) {
        return Map.of(
                "matrix", result.matrix(),
                "reward", result.reward(),
                "applied_winning_combinations", result.appliedWinningCombinations(),
                "applied_bonus_symbol", result.appliedBonusSymbol()
        );
    }
}
