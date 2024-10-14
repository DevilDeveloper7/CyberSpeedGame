package ru.devildeveloper74.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.devildeveloper74.model.symbol.Symbol;

import java.util.Set;

public class ProbabilitiesModule extends SimpleModule {

    public ProbabilitiesModule(Set<Symbol> symbolRegistry) {
//        addDeserializer(Probabilities.class, new StandardProbabilitiesDeserializer(symbolRegistry));
    }
}
