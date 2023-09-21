package ru.otus.application;

import ru.otus.db.DBConnector;
import ru.otus.query.QueryConstructor;
import ru.otus.utils.TableCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChooseGroupFilterWindow extends JFrame {
    JPanel grid = new JPanel();
    JFrame frame = new JFrame();
    JLabel label = new JLabel("Выберите из какой группы показать студентов:");
    JButton showStudentButton = new JButton("Показать студентов");
    JComboBox<String> groupCombobox;
    ChooseGroupFilterWindow(String[] groupNames) {
        super("Выбрать группу");
        GridLayout layout = new GridLayout(3, 1, 5, 12);
        grid.setLayout(layout);
        grid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        groupCombobox = new JComboBox<>(groupNames);
        showStudentButton.addActionListener(action -> {
            String groupName = groupCombobox.getSelectedItem().toString();
            String sqlQuery = new QueryConstructor().selectAllStudentsFromGroup(groupName);
            ResultSet resultSet = DBConnector.getDbConnector().executeQuery(sqlQuery);
            JTable table = null;
            try {
                table = TableCreator.convertResultSetToJTable(resultSet);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            new SqlResultWindow(sqlQuery, table);
            frame.dispose();
        });
        grid.add(label);
        grid.add(groupCombobox);
        grid.add(showStudentButton);
        frame.setContentPane(grid);
        frame.pack();
        frame.setSize(500, 200);
        frame.setVisible(true);
    }


}
