package ru.otus.repository;

import ru.otus.entity.Entity;

import java.util.List;
import java.util.Map;

public class CuratorRepository extends AbsRepository {
    @Override
    List<Entity> getEntityById(String id) {
        return null;
    }

    @Override
    Entity findByFieldСoncurrency(Map<String, String> fieldNameAndValue) {
        return null;
    }
}
