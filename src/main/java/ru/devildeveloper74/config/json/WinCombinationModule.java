package ru.devildeveloper74.config.json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.devildeveloper74.util.CombinationDeserializer;

import java.util.List;

public class WinCombinationModule extends SimpleModule {
    public WinCombinationModule() {
        addDeserializer(List.class, new CombinationDeserializer());
    }
}
