package ru.otus;

import ru.otus.application.Application;

public class Main {
    public static void main(String[] args) throws Exception {
        // 1. Можно ли определить конструктор в интерфейсе или абстрактном классе (для класса Entity)
        // 2. Проверить Exception у всех методов
        // 3. Исправить ObjectAsArray в Entity
        // 5. Переписать метод getRepresentationData с использованием Reflection
        // 7. Куда вынести обработчики в классе VisualWindow
        // 8. Повесить удаление таблиц на событие закрытия главного окна
        // 9. ResultSet c Generic
        // JUnit 5.
        new Application().createTables().fillTablesWithData().getRepresentationData();
    }
}
