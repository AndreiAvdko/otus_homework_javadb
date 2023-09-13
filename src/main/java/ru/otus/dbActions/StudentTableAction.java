package ru.otus.dbActions;

import ru.otus.db.DBConnector;
import ru.otus.dbQuery.GetDataQuery;
import ru.otus.entity.Sex;
import ru.otus.entity.StudentEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static ru.otus.dbQuery.GetDataQuery.getAllFromTableQuerry;

public class StudentTableAction implements ITableAction {


    @Override
    public List<StudentEntity> getAllEntity() {
        DBConnector dbConnector = DBConnector.getDbConnector();
        List<StudentEntity> studentList = new LinkedList<>();
        List<String> parameters = new LinkedList<String>();

        String sqlRequest = String.format(getAllFromTableQuerry, "\"Student\"");
        List listStudentsString = dbConnector.executeQueryWithPreparedStmt(sqlRequest, parameters, 4);
        for (int i = 0; i < listStudentsString.size(); i++) {
            List<String> listParams = (List<String>) listStudentsString.get(i);
            int id = Integer.parseInt(listParams.get(0));
            String name = listParams.get(1);
            int age = Integer.parseInt(listParams.get(3));
            Sex sex = null;
            switch (listParams.get(2)) {
                case "M": sex = Sex.MALE; break;
                case "F": sex = Sex.FEMALE; break;
            }
            studentList.add(new StudentEntity(id, name, age, sex));
        }
        studentList.forEach(System.out::println);
        return null;
    }

    @Override
    public StudentEntity getEntityByID(int id) {
        return null;
    }
}
