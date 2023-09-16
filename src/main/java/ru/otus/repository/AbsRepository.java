package ru.otus.repository;

import ru.otus.db.DBConnector;
import ru.otus.entity.Entity;
import ru.otus.entity.Student;
import ru.otus.query.QueryConstructor;
import ru.otus.tables.PostgresTable;
import ru.otus.utils.MetaClassData;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class AbsRepository {
    List<Entity> entities;

public List<Entity> getAllEntity(Class entityClass) throws SQLException {
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

    abstract List<Entity> getEntityById (String id);

    abstract Entity findByFieldСoncurrency (Map<String, String> fieldNameAndValue);
}
