package ru.otus.dbActions;

import com.github.javafaker.Faker;
import ru.otus.db.DBConnector;
import ru.otus.entity.Sex;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static ru.otus.dbQuery.FillDataQuerry.fillCuratorTableQuerry;
import static ru.otus.dbQuery.FillDataQuerry.fillStudentTableQuerry;

public class FillWithData {
    static Faker faker = new Faker();
    public FillWithData fillStudentTable (int numberOfRecords) {
        DBConnector dbConnector = DBConnector.getDbConnector();
        Random random = new Random();
        for (int i = 0; i < numberOfRecords; i++) {
            List<String> studentParameter = new LinkedList<>();
            studentParameter.add(faker.name().firstName());
            studentParameter.add(String.valueOf(Math.abs(random.nextInt()%100)));
            studentParameter.add(Sex.MALE.getDescription());
            dbConnector.executeWithPreparedStmnt(fillStudentTableQuerry, studentParameter);
        }
        return this;
    }

    public FillWithData fillCuratorTable(int numberOfRecords) {
        DBConnector dbConnector = DBConnector.getDbConnector();
        for (int i = 0; i < numberOfRecords; i++) {
            List<String> curatorParameter = new LinkedList<>();
            curatorParameter.add(faker.name().firstName());
            dbConnector.executeWithPreparedStmnt(fillCuratorTableQuerry, curatorParameter);
        }
        return this;
    }

    public FillWithData fillGroupTable(int numberOfRecords) {

        return this;
    }
}
