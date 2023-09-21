package ru.otus.query;

import java.util.Map;

public interface IQuery {
    String createTableQuery(String tableName, Map<String,String> tableFields);
    String insertTableQuery(String tableName, Map<String,String> fieldsAndValues);
    Integer howMuchEntityInTableQuery(String tableName);
    String selectAllFromTableQuery(String tableName);
    String dropTableQuery(String tableName);

}
