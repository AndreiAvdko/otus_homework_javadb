package ru.otus.repository;

import ru.otus.db.DBConnector;
import ru.otus.entity.Entity;
import ru.otus.query.QueryConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class StudentRepository extends AbsRepository {
    @Override
    public List<Entity> getEntityById(String id) {
        return null;
    }

    @Override
    public Entity findByFieldСoncurrency(Map<String, String> fieldNameAndValue) {
        return null;
    }
}
