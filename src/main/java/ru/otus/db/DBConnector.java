package ru.otus.db;

import ru.otus.settings.Settings;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class DBConnector implements IDBConnector {

    // поле класса для реализации Синглтона
    private static DBConnector dbConnector = null;
    private Map<String, String> settings;
    private static Connection connection = null;
    private static Statement statement = null;


    // Закрытый конструктор
    private DBConnector() {
    }

    // получение объекта "одиночки"
    public static DBConnector getDbConnector() {
        if(dbConnector == null) {
            dbConnector = new DBConnector();
            dbConnector.settings = new Settings().getDbSettings();
        }

        return dbConnector;
    }

    private void open() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(settings.get("db_url") + "/" + settings.get("db_name"), settings.get("username"), settings.get("password"));
            }
            if (statement == null) {
                statement = connection.createStatement();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        if (statement != null) {
            try {
                statement.close();
                statement = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ResultSet executeQuery(String query) {
        this.open();
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // this.close();
        }
        return result;
    }

    public void execute(String sqlRequest) {
        this.open();
        try {
            statement.execute(sqlRequest);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.close();
        }
    }
}
