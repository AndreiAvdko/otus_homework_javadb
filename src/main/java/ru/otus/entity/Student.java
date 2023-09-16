package ru.otus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.utils.DataGenerator;

import java.util.Map;
import java.util.Random;

@Data
@AllArgsConstructor
public class Student implements Entity {
    // Вывести на экран информацию о всех студентах включая название группы и имя куратора
    // Вывести на экран количество студентов
    // Вывести на экран количество студенток
    // Используя вложенные запросы вывести на экран студентов из определенной группы(поиск по имени группы)
    // 15 студентов
    // id, fio, sex, id_group

    int id;
    int id_group;
    String name;
    String surname;
    String patronymic;
    String sex;

    public Student() {
        Map<String, String> studentData = DataGenerator.generateFullNameData();
        Random random = new Random();
        this.id_group = random.nextInt(1,4);
        this.name = studentData.get("name");
        this.surname = studentData.get("surname");
        this.patronymic = studentData.get("patronymic");
        this.sex = studentData.get("sex");
    }

    public Student(Map<String, String> fieldValue) {
        this.id = Integer.parseInt(fieldValue.get("id"));
        this.id_group = Integer.parseInt(fieldValue.get("id_group"));
        this.name = fieldValue.get("name");
        this.surname = fieldValue.get("surname");
        this.patronymic = fieldValue.get("patronymic");
        this.sex = fieldValue.get("sex");
    }
}
