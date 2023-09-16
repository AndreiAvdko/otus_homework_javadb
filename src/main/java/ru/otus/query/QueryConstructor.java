package ru.otus.query;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class QueryConstructor implements IQuery {
    @Override
    public String createTableQuery(String tableName, Map<String, String> tableFields) {
        String sqlQuery = String.format("CREATE TABLE IF NOT EXISTS \"%s\" (", tableName);
        for (String fieldName : tableFields.keySet()) {
            String fieldPostgresType = tableFields.get(fieldName);
            if (fieldName.equals("id")) {
                sqlQuery+= " " + fieldName + " integer generated always as identity primary key,";
            } else {
                sqlQuery+= " " + fieldName + " " + fieldPostgresType + ",";
            }
        }
        sqlQuery = StringUtils.removeEnd(sqlQuery, ",");
        sqlQuery += ");";

        return sqlQuery;
    }

    @Override
    public String dropTableQuery(String tableName) {
        String sqlQuery = String.format("DROP TABLE \"%s\"", tableName);
        return sqlQuery;
    }

    @Override
    public String insertTableQuery(String tableName, Map<String, String> fieldsAndValues) {
        String sqlQuery = String.format("INSERT INTO \"%s\" ", tableName);
        String fieldNameInQuery = "(";
        String valuesFieldInQuery = "(";
        for (Map.Entry<String, String> entry: fieldsAndValues.entrySet())
        {
            if (entry.getKey() != "id") {
                String field = entry.getKey();
                String value = entry.getValue();
                fieldNameInQuery += String.format("%s, ", field);
                valuesFieldInQuery += String.format("'%s', ", value);
            }
        }
        fieldNameInQuery = StringUtils.removeEnd(fieldNameInQuery, ", ");
        fieldNameInQuery += ")";
        valuesFieldInQuery = StringUtils.removeEnd(valuesFieldInQuery, ", ");
        valuesFieldInQuery += ")";

        sqlQuery += String.format("%s VALUES %s;", fieldNameInQuery, valuesFieldInQuery);

        return sqlQuery;
    }

    @Override
    public String selectAllFromTableQuery(String tableName) {
        String sqlQuery = String.format("SELECT * FROM \"%s\";", tableName);
        return sqlQuery;
    }

}
