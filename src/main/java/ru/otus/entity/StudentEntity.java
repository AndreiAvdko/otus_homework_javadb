package ru.otus.entity;


import lombok.*;

@Data
@ToString
@AllArgsConstructor
public class StudentEntity {

    int id;
    @NonNull
    String name;
    @NonNull
    int age;
    Sex sex;


}
