package ru.otus.dbActions;

import ru.otus.entity.StudentEntity;

import java.util.List;

public interface ITableAction {
    List getAllEntity();

    StudentEntity getEntityByID(int id);
}
