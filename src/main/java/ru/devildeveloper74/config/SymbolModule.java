package ru.devildeveloper74.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.devildeveloper74.util.SymbolDeserializer;

import java.util.Set;

public class SymbolModule extends SimpleModule {
    public SymbolModule() {
        addDeserializer(Set.class, new SymbolDeserializer());
    }
}
