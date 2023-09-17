package ru.otus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.utils.DataGenerator;

import java.util.Map;

@Data
@AllArgsConstructor
public class Curator implements Entity {
    int id;
    String fio;
    /*String name;
    String surname;
    String patronymic;*/

    public Curator() {
        Map<String, String> studentData = DataGenerator.generateFullNameData();
        this.fio = String.format("%s %s %s", studentData.get("name"), studentData.get("surname"), studentData.get("patronymic"));
        /*this.name = studentData.get("name");
        this.surname = studentData.get("surname");
        this.patronymic = studentData.get("patronymic");*/
    }

    public Curator(Map<String, String> fieldValue) {
        this.id = Integer.parseInt(fieldValue.get("id"));
        this.fio = fieldValue.get("fio");
        /*this.name = fieldValue.get("name");
        this.surname = fieldValue.get("surname");
        this.patronymic = fieldValue.get("patronymic");*/
    }

    @Override
    public String[] toArray() {
        String[] objectAsArray = { String.valueOf(id),
                                   /*name,
                                   surname,
                                   patronymic*/
                                    fio};
        return objectAsArray;
    }
}
