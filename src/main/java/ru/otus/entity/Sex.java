package ru.otus.entity;

public enum Sex {
    MALE("M"),
    FEMALE("F");

    private final String description;

    private Sex(String name) {
        this.description = name;
    }
    public String getDescription() {
        return this.description;
    }

}
