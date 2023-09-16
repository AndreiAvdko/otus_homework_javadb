package ru.otus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.utils.DataGenerator;

import java.util.Map;

@Data
@AllArgsConstructor
public class Curator implements Entity {
    // 4 куратора
    // id,
    // fio

    int id;
    String name;
    String surname;
    String patronymic;

    public Curator() {
        Map<String, String> studentData = DataGenerator.generateFullNameData();
        this.name = studentData.get("name");
        this.surname = studentData.get("surname");
        this.patronymic = studentData.get("patronymic");
    }

    public Curator(Map<String, String> fieldValue) {
        this.id = Integer.parseInt(fieldValue.get("id"));
        this.name = fieldValue.get("name");
        this.surname = fieldValue.get("surname");
        this.patronymic = fieldValue.get("patronymic");
    }
}
