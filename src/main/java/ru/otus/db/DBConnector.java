package ru.otus.db;

import org.apache.commons.lang3.StringUtils;
import ru.otus.settings.Settings;

import java.sql.*;
import java.util.LinkedList;
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

    public List executeQuery() {
        return null;
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

    public List executeQueryWithPreparedStmt(String sqlRequest, List<String> parameters, int entityParamsNumber) {
        List<List<String>> listStringEntity = new LinkedList<>();
        this.open();
        try (PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM \"Student\" " + ";")) {
            // Если есть параметры для запроса
            if (parameters.size() > 0) {
                for (int i = 0; i < parameters.size(); i++) {
                    if (StringUtils.isNumeric(parameters.get(i))) {
                        statement.setInt(i+1, Integer.parseInt(parameters.get(i)));
                    } else {
                        statement.setString(i+1, parameters.get(i));
                    }
                }
            }
            // Выполняем запрос
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                List<String> entityParams = new LinkedList<>();
                for (int i = 0; i < entityParamsNumber; i++) {
                    try {
                        entityParams.add(String.valueOf(resultSet.getInt(i+1)));
                    } catch (SQLException e) {
                        entityParams.add(resultSet.getString(i+1));
                    }
                }
                listStringEntity.add(entityParams);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listStringEntity;
    }

    public void executeWithPreparedStmnt(String sqlRequest, List<String> parameters) {
        this.open();
        try (PreparedStatement statement =
                     connection.prepareStatement(sqlRequest)) {
            for (int i = 0; i < parameters.size(); i++) {
                if (StringUtils.isNumeric(parameters.get(i))) {
                    statement.setInt(i+1, Integer.parseInt(parameters.get(i)));
                } else {
                    statement.setString(i+1, parameters.get(i));
                }
            }
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
