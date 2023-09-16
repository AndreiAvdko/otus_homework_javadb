package ru.otus;

import ru.otus.application.Application;
import ru.otus.db.DBConnector;
import ru.otus.entity.Curator;
import ru.otus.entity.Entity;
import ru.otus.entity.Group;
import ru.otus.entity.Student;
import ru.otus.repository.CuratorRepository;
import ru.otus.repository.GroupRepository;
import ru.otus.repository.StudentRepository;
import ru.otus.tables.PostgresTable;
import ru.otus.utils.MetaClassData;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        // 1. Можно ли определить конструктор в интерфейсе или абстрактном классе (для Entity)
        // 2. Проверить Exception у всех методов
        /*new Application().createTables()
                         .fillTablesWithData()
                         .dropTables();*/


        List<Entity> studentList = new StudentRepository().getAllEntity(Student.class);
        studentList.forEach(System.out::println);
        System.out.println("________________________________");
        List<Entity> curatorList = new CuratorRepository().getAllEntity(Curator.class);
        curatorList.forEach(System.out::println);
        System.out.println("________________________________");
        List<Entity> groupList = new GroupRepository().getAllEntity(Group.class);
        groupList.forEach(System.out::println);
        System.out.println("________________________________");
    }
}
