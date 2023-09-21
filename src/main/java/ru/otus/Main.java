package ru.otus;

import ru.otus.application.Application;

public class Main {
    public static void main(String[] args) {
        // Удаление таблиц происходит при закрытии главного окна программы
        new Application().createTables().fillTablesWithData().getRepresentationData();
    }
}
