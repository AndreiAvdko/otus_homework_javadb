package ru.otus.utils;

import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DataGenerator {
    public static Faker faker = new Faker(new Locale("ru"));

    public static Map<String, String> generateFullNameData() {
        Faker faker = new Faker(new Locale("ru"));
        Map <String, String> fullNameData = new HashMap<>();
        while(true) {
            String generateName = faker.name().fullName();
            String[] fullName = generateName.split(" ");
                if (fullName.length == 3 && (fullName[2].contains("вич") || fullName[2].contains("вна"))) {
                    fullNameData.put("surname", fullName[0]);
                    fullNameData.put("name", fullName[1]);
                    fullNameData.put("patronymic", fullName[2]);
                    String sex;
                    if (fullName[2].contains("вич")) {
                        sex = "М";
                    } else {
                        sex = "Ж";
                    }
                    fullNameData.put("sex", sex);
                    break;
                }
        }
        return fullNameData;
    }

}
