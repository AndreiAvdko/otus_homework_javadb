package ru.otus.entity;
import ru.otus.db.DBConnector;
import ru.otus.query.QueryConstructor;
import ru.otus.tables.PostgresTable;
import ru.otus.utils.MetaClassData;

import javax.swing.*;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public interface Entity {
    String[] toArray();
    default public List<Entity> getAllEntity() throws SQLException {
        Class entityClass = this.getClass();
        List<Entity> entityList = new LinkedList<>();
        String selectAllQuery = new QueryConstructor().selectAllFromTableQuery(entityClass.getSimpleName());
        ResultSet result = DBConnector.getDbConnector().executeQuery(selectAllQuery);
        Map<String, String> fieldsEntityValue = new HashMap<>();
        Map<String, String> fields = MetaClassData.getFields(entityClass, new PostgresTable());
        while (result.next()) {
            for (String fieldName : fields.keySet()) {
                try {
                    fieldsEntityValue.put(fieldName, String.valueOf(result.getInt(fieldName)));
                } catch (SQLException e) {
                    fieldsEntityValue.put(fieldName, result.getString(fieldName));
                }
            }
            Class [] parameterType = {Map.class};
            try {
                entityList.add((Entity) entityClass.getDeclaredConstructor(parameterType)
                        .newInstance(fieldsEntityValue));
            } catch (Exception e) {
                System.out.println("Не был обнаружен нужный конструктор у класса или его не получилось вызвать");
            }

            fieldsEntityValue.clear();
        }
        return entityList;
    }

    default public JTable convertToJTable() throws Exception {
        List<Entity> entitytList = this.getAllEntity();

        Object[] headers = Arrays.stream(this.getClass().getDeclaredFields()).map(Field::getName).toArray(x -> new String[x]);
        Object[][] rows = new Object[entitytList.size()][];

        String tableTitle = this.getClass().getSimpleName();
        if (!entitytList.isEmpty()) {
            for (int i = 0; i < entitytList.size(); i++) {
                rows[i] = entitytList.get(i).toArray();
            }
            return new JTable(rows, headers);
        }
        return new JTable(rows, headers);
    }

    default public Integer howMuchThisEntity() {
        return new QueryConstructor().howMuchEntityInTableQuery(this.getClass().getSimpleName());
    }
}
