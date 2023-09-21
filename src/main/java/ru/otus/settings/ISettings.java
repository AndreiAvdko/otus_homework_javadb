package ru.otus.settings;

import java.util.Map;

public interface ISettings {
    Map<String, String> getDbSettings();
    Map<String, Integer> getApplicationEntitySettings();

}
