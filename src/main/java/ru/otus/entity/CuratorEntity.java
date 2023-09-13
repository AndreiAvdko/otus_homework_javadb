package ru.otus.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class CuratorEntity {
    @NonNull
    int id;
    @NonNull
    String name;
}
