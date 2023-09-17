package ru.otus;

import ru.otus.application.Application;
import ru.otus.db.DBConnector;
import ru.otus.utils.TableCreator;

import javax.swing.*;
import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) throws Exception {
        // 1. Можно ли определить конструктор в интерфейсе или абстрактном классе (для Entity)
        // 2. Проверить Exception у всех методов
        // 3. Исправить ObjectAsArray в Entity
        // 4. Join запрос как сделать лучше???
        // 5. Переписать метод getRepresentationData с использованием Reflection
        // 6. Ссылки на функции :: в stream Api
        // 7. Куда вынести обработчики в классе VisualWindow
        //new Application().createTables().fillTablesWithData().getRepresentationData().dropTables();
        //new Application().createTables().fillTablesWithData();

        new Application().getRepresentationData();
       /* Map<String,String> where = new HashMap<String, String>(){{
            put("sex","М");
        }};


*/


    }
}
