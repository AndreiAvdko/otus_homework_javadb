package ru.otus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.utils.DataGenerator;

import java.util.Map;

@Data
@AllArgsConstructor
public class Curator implements Entity {
    private int id;
    private String fio;

    public Curator() {
        Map<String, String> studentData = DataGenerator.generateFullNameData();
        this.fio = String.format("%s %s %s", studentData.get("name"), studentData.get("surname"), studentData.get("patronymic"));
    }

    public Curator(Map<String, String> fieldValue) {
        this.id = Integer.parseInt(fieldValue.get("id"));
        this.fio = fieldValue.get("fio");
    }

    @Override
    public String[] toArray() {
        String[] objectAsArray = {
                String.valueOf(id), fio
        };
        return objectAsArray;
    }
}
