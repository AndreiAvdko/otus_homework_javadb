package ru.otus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.db.DBConnector;
import ru.otus.settings.Settings;
import ru.otus.utils.DataGenerator;

import java.util.Map;
import java.util.Random;

@Data
@AllArgsConstructor
public class Student implements Entity {
    int id;
    int id_group;
    String fio;
    String sex;

    public Student() {
        Map<String, String> studentData = DataGenerator.generateFullNameData();
        Random random = new Random();
        try {
            Integer groupCount = new Settings().getApplicationEntitySettings().get("Group");
            this.id_group = random.nextInt(1, groupCount + 1);
        } catch (Exception e) {
            this.id_group = random.nextInt(1,4);
        }

        this.fio = String.format("%s %s %s", studentData.get("name"), studentData.get("surname"), studentData.get("patronymic"));
        this.sex = studentData.get("sex");
    }

    public Student(Map<String, String> fieldValue) {
        this.id = Integer.parseInt(fieldValue.get("id"));
        this.id_group = Integer.parseInt(fieldValue.get("id_group"));
        this.fio = fieldValue.get("fio");
        this.sex = fieldValue.get("sex");
    }

    @Override
    public String[] toArray() {
        String[] objectAsArray = { String.valueOf(id),
                                   String.valueOf(id_group),
                                   fio,
                                   sex };
        return objectAsArray;
    }
}
