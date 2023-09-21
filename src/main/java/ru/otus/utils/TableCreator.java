package ru.otus.utils;

import ru.otus.settings.Settings;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/*
*   Класс, содержащий, метод для преобразования данных, полученных из БД в Swing-таблицу JTable
 */
public class TableCreator {

    public static JTable convertResultSetToJTable (ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<String>();
        Set<Integer> notNeedToAdd = new HashSet<>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            if (metaData.getColumnName(column).contains("id")) {
                notNeedToAdd.add(column);
            } else {
                String colName = metaData.getColumnName(column);
                String tableName = metaData.getTableName(column);
                columnNames.add(tableName + " " + colName);
            }
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (resultSet.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                if (!notNeedToAdd.contains(columnIndex)) {
                    vector.add(resultSet.getObject(columnIndex));
                }
            }
            data.add(vector);
        }
        return new JTable(new DefaultTableModel(data, columnNames));
    }
}
