package ru.otus.tables;

import ru.otus.entity.Entity;

public interface ITable {
    void createTable(Class parameterClass);
    void insertTableEntry(Entity entity);
    public void dropTable(Class parameterClass);
    String getTypeForConcreteDb (String javaValueType);
}
