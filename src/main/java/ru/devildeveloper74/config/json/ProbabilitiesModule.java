package ru.devildeveloper74.config.json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.devildeveloper74.model.probability.Probabilities;
import ru.devildeveloper74.model.symbol.Symbol;
import ru.devildeveloper74.util.ProbabilitiesDeserializer;

import java.util.Set;

public class ProbabilitiesModule extends SimpleModule {
    public ProbabilitiesModule(Set<Symbol> symbolRegistry) {
        addDeserializer(Probabilities.class, new ProbabilitiesDeserializer(symbolRegistry));
    }
}
