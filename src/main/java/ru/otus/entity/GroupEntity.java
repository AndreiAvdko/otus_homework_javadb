package ru.otus.entity;


import lombok.Data;
import lombok.NonNull;

@Data
public class GroupEntity {
    @NonNull
    int id;
    @NonNull
    String name;
    String curatorName;
}
