package ru.otus;

import ru.otus.dbActions.StudentTableAction;
import ru.otus.service.Application;

import java.sql.PreparedStatement;


public class Main {
    public static void main(String[] args) {
//        Вопросы:

//        1. Где хранить константы
//        3.  Можно ли использовать Lombok
//        4. Нужны ли foreign key? (Сначала заполняем Curator, затем Group)
//        6. Можно ли использовать название таблицы как параметр для PreparedStatement?
//        select * from ?
//        7. __Swing__ или Fx?


//        8. Паттерн DAO ?
//        RefactorCode
//        1. Добавить EnumEntity для существующих сущностей


        new Application().run();
    }
}
