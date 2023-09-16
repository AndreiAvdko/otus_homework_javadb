package ru.otus.query;

import java.util.Map;

public interface IQuery {
    String createTableQuery(String tableName, Map<String,String> tableFields);
    String dropTableQuery(String tableName);

    String insertTableQuery(String tableName, Map<String,String> fieldsAndValues);

    String selectAllFromTableQuery(String tableName);

    // String updateTableQuery();


}
