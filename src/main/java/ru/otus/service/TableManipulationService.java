package ru.otus.service;

import ru.otus.db.DBConnector;
import ru.otus.dbActions.FillWithData;

import static ru.otus.dbQuery.CreateTableQuery.*;
import static ru.otus.dbQuery.DropTableQuerry.*;

public class TableManipulationService {

    public static void createTableInDb () {
        DBConnector dbConnector = DBConnector.getDbConnector();
        dbConnector.execute(createStudentTableQuerry);
        dbConnector.execute(createCuratorTableQuerry);
        dbConnector.execute(createGroupTableQuerry);
        dbConnector.execute(createGroupTableQuerry);
    }

    public static void dropAllTable() {
        DBConnector dbConnector = DBConnector.getDbConnector();
        dbConnector.execute(dropStudentTableQuerry);
        dbConnector.execute(dropGroupTableQuerry);
        dbConnector.execute(dropCuratorTableQuerry);
    }

    public static void fillTableWithFakeData(int studentRecordNumber,
                                             int curatorRecordNumber,
                                             int groupRecordNumber) {
        new FillWithData().fillStudentTable(studentRecordNumber)
                                .fillCuratorTable(curatorRecordNumber)
                                .fillGroupTable(groupRecordNumber);
    }
}
