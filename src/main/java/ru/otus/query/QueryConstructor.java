package ru.otus.query;

import org.apache.commons.lang3.StringUtils;
import ru.otus.db.DBConnector;
import ru.otus.entity.Entity;

import java.beans.Introspector;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    @Override
    public Integer howMuchEntityInTableQuery(String tableName) {
        String sqlQuery = String.format("SELECT count(*) FROM \"%s\";", tableName);
        Integer entityCount = 0;
        try {
            ResultSet result = DBConnector.getDbConnector().executeQuery(sqlQuery);
            while (result.next()) {
                entityCount = result.getInt("count");
            }
        } catch (SQLException e) {
            System.out.println("Не удалось получить о количестве записей в таблице " + tableName);
        }
        return entityCount;
    }

    public String sortByConditionWhereFieldsEqualValues(String tableName, Map<String, String> fieldEqualVaue) {
        String sqlQuery = String.format("SELECT * FROM \"%s\" ", tableName);
        //SELECT * FROM "Student" where sex = 'М';
        String whereQueryPart = "WHERE ";
        for (Map.Entry<String, String> entry : fieldEqualVaue.entrySet()) {
            whereQueryPart += String.format("%s = '%s' AND ", entry.getKey(), entry.getValue());
        }
        whereQueryPart = StringUtils.removeEnd(whereQueryPart, " AND ");
        sqlQuery += whereQueryPart;
        sqlQuery += ";";
        return sqlQuery;
    }

    public String selectAllStudentsFromGroup(String groupName) {
        String query = String.format("SELECT * FROM \"Student\" " +
                "JOIN \"Group\" ON id_group = \"Group\".id " +
                "WHERE id_group " +
                "= (SELECT id FROM \"Group\" WHERE name = '%s');", groupName);
        return query;
    }

    public String updateCuratorInGroupQuery(String groupName, String curatorFio) {
    return String.format("UPDATE \"Group\" SET id_curator = (SELECT id FROM \"Curator\" WHERE fio = '%s') " +
                "WHERE \"Group\".id = (SELECT id FROM \"Group\" WHERE name = '%s');", curatorFio, groupName);
    }

    public String currentGroupCurator (String groupName) {
        return String.format("select * from \"Curator\" join \"Group\" on \"Curator\".id = id_curator where \"Group\".name = '%s';", groupName);
    }



}
