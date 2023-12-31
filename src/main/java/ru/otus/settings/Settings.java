package ru.otus.settings;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Settings implements ISettings {

    @Override
    public Map<String, String> getDbSettings() {
        Properties properties = new Properties();
        Map<String, String> result = new HashMap<>();
        try {
            properties.load(new FileReader( System.getProperty("user.dir") +
                    "/src/main/java/ru/otus/settings/db.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(String key: properties.stringPropertyNames()) {
            result.put(key, properties.getProperty(key));
        }
        return result;
    }

    @Override
    public Map<String, Integer> getApplicationEntitySettings() {
        Properties properties = new Properties();
        Map<String, Integer> result = new HashMap<>();
        try {
            properties.load(new FileReader( System.getProperty("user.dir") +
                    "/src/main/java/ru/otus/settings/appEntityCount.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(String key: properties.stringPropertyNames()) {
            Integer value = Integer.parseInt(properties.getProperty(key));
            result.put(key, value);
        }
        return result;
    }



}
