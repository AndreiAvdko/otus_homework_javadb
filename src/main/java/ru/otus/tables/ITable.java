package ru.otus.tables;

import ru.otus.entity.Entity;

public interface ITable {
    void createTable(Class parameterClass);
    void insertTableEntry(Entity entity);
    public void dropTable(Class parameterClass);

    // void update();
    // void delete();
    // void read();

    String getTypeForConcreteDb (String javaValueType);
}
