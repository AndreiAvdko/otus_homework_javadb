package ru.otus.tables;

import ru.otus.db.DBConnector;
import ru.otus.entity.Entity;
import ru.otus.query.QueryConstructor;
import ru.otus.utils.MetaClassData;

import java.util.HashMap;
import java.util.Map;

public class PostgresTable implements ITable {
    static DBConnector dbConnector;
    String name = "null";
    Map<String, String> tableFields = new HashMap<>();

    static Map<String,String> typeMatchingJavaToPostgres = new HashMap<String, String>(){{
        put("String","varchar");
        put("int","integer");
    }};

    public PostgresTable() {
        dbConnector = DBConnector.getDbConnector();
    }

    public void createTable(Class parameterClass) {
        Map<String,String> tableFields = new HashMap<>();
        String tableName = MetaClassData.getClassName(parameterClass);
        tableFields = MetaClassData.getFields(parameterClass, this);
        String sqlQuery = new QueryConstructor().createTableQuery(tableName, tableFields);

        dbConnector.execute(sqlQuery);
    }

    @Override
    public void insertTableEntry(Entity entity) {
        String tableName = MetaClassData.getClassName(entity);
        Map<String, String> fieldsAndValues = MetaClassData.getFieldsAndValues(entity);
        String sqlQuery = new QueryConstructor().insertTableQuery(tableName, fieldsAndValues);

        dbConnector.execute(sqlQuery);
    }

    @Override
    public String getTypeForConcreteDb(String javaValueType) {
        return typeMatchingJavaToPostgres.get(javaValueType);
    }

    public void dropTable(Class parameterClass) {
        String tableName = MetaClassData.getClassName(parameterClass);
        String sqlQuery = new QueryConstructor().dropTableQuery(tableName);
        dbConnector.execute(sqlQuery);
    }

    public void updateTable() {

    }
}
