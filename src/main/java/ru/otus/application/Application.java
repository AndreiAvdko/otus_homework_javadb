package ru.otus.application;

import ru.otus.entity.Curator;
import ru.otus.entity.Entity;
import ru.otus.entity.Group;
import ru.otus.entity.Student;
import ru.otus.settings.Settings;
import ru.otus.tables.PostgresTable;

import javax.swing.*;
import java.util.Map;

public class Application {
    Map<String, Integer> entityCount;
    static VisualWindow window = new VisualWindow();
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

    public Application getRepresentationData() throws Exception {
        JTable table = new Student().convertToJTable();
        JTable table1 = new Curator().convertToJTable();
        JTable table2 = new Group().convertToJTable();
        window.addTable(table, "Таблица Студенты. Кол-во студентов: " + new Student().howMuchThisEntity());
        window.addTable(table1, "Таблица Кураторы. Кол-во кураторов: " + new Curator().howMuchThisEntity());
        window.addTable(table2, "Таблица Группы. Количество групп: " + new Group().howMuchThisEntity());
        window.showWindow();
        return this;
    }

    public Application mainWorkflow() {
        return this;
    }
}
