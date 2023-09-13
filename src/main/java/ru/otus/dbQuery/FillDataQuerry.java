package ru.otus.dbQuery;

import com.github.javafaker.Faker;

public class FillDataQuerry {
    public static final String fillStudentTableQuerry = "INSERT INTO \"Student\"(name, age, sex) values (?, ?, ?);";
    public static final String fillCuratorTableQuerry = "INSERT INTO \"Curator\"(name) values (?);";
    public static final String fillGroupTableQuerry = "INSERT INTO \"Group\"(name, curator_id) values (?, ?);";

}
