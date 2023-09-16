package ru.otus.application;

import jdk.javadoc.internal.doclets.formats.html.Table;
import ru.otus.entity.Entity;
import ru.otus.settings.Settings;
import ru.otus.tables.PostgresTable;

import java.util.Map;

public class Application {
    Map<String, Integer> entityCount;
    public Application() {
        entityCount = new Settings().getApplicationEntitySettings();
    }
    public Application createTables() {
        for (String className : entityCount.keySet()) {
            try {
                new PostgresTable().createTable(Class.forName("ru.otus.entity." + className));
            } catch (ClassNotFoundException e) {
                System.out.println("Не был найден класс с названием " + className);
            }
        }
        return this;
    }
    public Application fillTablesWithData() {
        PostgresTable postgresTable = new PostgresTable();
        for (String className : entityCount.keySet()) {
            for (int i = 0; i < entityCount.get(className); i++) {
                Class entityClass;
                try {
                    entityClass = Class.forName("ru.otus.entity."+ className);
                    Object entity = entityClass.getDeclaredConstructor().newInstance();
                    postgresTable.insertTableEntry((Entity)entity);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // new PostgresTable().insertTableEntry();
            }
        }
        return this;
    }

    public void dropTables(){
        for (String className : entityCount.keySet()) {
            try {
                new PostgresTable().dropTable(Class.forName("ru.otus.entity." + className));
            } catch (ClassNotFoundException e) {
                System.out.println("Не был найден класс с названием " + className);
            }
        }
    }
}
