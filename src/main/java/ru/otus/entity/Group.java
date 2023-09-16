package ru.otus.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.Random;

@Data
@AllArgsConstructor
public class Group implements Entity {
    // Обновить данные по группе сменив куратора
    // Вывести список групп с их кураторами
    // 3 группы
    // id, name, id_curator

    int id;
    int id_curator;
    String name;

    public Group() {
        Random random = new Random();
        this.id_curator = random.nextInt(1, 5);
    }

    public Group(Map<String, String> fieldValue) {
        this.id = Integer.parseInt(fieldValue.get("id"));
        this.id_curator = Integer.parseInt(fieldValue.get("id"));
        this.name = fieldValue.get("id");
    }

}
