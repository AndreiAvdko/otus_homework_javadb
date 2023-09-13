package ru.otus.service;

import ru.otus.dbActions.StudentTableAction;

public class Application {
    public void run() {
        TableManipulationService.createTableInDb();
        // Сначала создаётся таблица с Кураторами, затем с Группами (получаем id всех кураторов и назначаем к каждой группе)
        TableManipulationService.fillTableWithFakeData(5, 5, 5);

        new StudentTableAction().getAllEntity();

        /*try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
         TableManipulationService.dropAllTable();
    }
}
