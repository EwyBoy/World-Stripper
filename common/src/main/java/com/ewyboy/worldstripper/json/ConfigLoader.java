package com.ewyboy.worldstripper.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ConfigLoader {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static <T> T loadConfig(Class<T> configClass, File configFile) {
        if (!configFile.exists()) {
            T defaultConfig = createDefaultConfig(configClass);
            saveConfig(defaultConfig, configFile);
            return defaultConfig;
        }
        try (FileReader reader = new FileReader(configFile)) {
            return gson.fromJson(reader, configClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void saveConfig(T config, File configFile) {
        try (FileWriter writer = new FileWriter(configFile)) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T createDefaultConfig(Class<T> configClass) {
        Constructor<T> constructor;
        try {
            constructor = configClass.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        try {
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T applyOverrides(T config, Map<String, Object> overrides) throws NoSuchFieldException, IllegalAccessException {
        for (Map.Entry<String, Object> entry : overrides.entrySet()) {
            Field field = config.getClass().getDeclaredField(entry.getKey());
            field.setAccessible(true);
            Object value = entry.getValue();

            if (value instanceof Map) {
                value = applyOverrides(field.get(config), (Map<String, Object>) value);
            }

            field.set(config, value);
        }
        return config;
    }

}

