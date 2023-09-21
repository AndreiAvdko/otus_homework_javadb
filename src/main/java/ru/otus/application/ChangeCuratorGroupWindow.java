package ru.otus.application;

import ru.otus.db.DBConnector;
import ru.otus.entity.Group;
import ru.otus.query.QueryConstructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class ChangeCuratorGroupWindow extends JFrame {
    JLabel currentCuratorLabel;
    JComboBox<String> groupCombobox;
    JPanel grid = new JPanel();
    JFrame frame = new JFrame();
    public ChangeCuratorGroupWindow(String[] groupNames, String[] curatorNames) {
        super("Сменить куратора у группы");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    new Application().getRepresentationData();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        GridLayout layout = new GridLayout(5, 1, 5, 12);
        grid.setLayout(layout);

        grid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel label = new JLabel("Выберите название группы и куратора для неё");



        groupCombobox = new JComboBox<>(groupNames);

        try {
            currentCuratorLabel = new JLabel("Текущий куратор у этой группы - " + new Group().getGroupCurator(groupCombobox.getSelectedItem().toString()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        groupCombobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    currentCuratorLabel.setText("Текущий куратор у этой группы - " + new Group().getGroupCurator(groupCombobox.getSelectedItem().toString()));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JComboBox<String> curatorComboBox = new JComboBox<>(curatorNames);

        JButton changeCuratorButton = new JButton("Сменить куратора");
        changeCuratorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String groupName = groupCombobox.getSelectedItem().toString();
                String curatorName = curatorComboBox.getSelectedItem().toString();

                String sqlQuery = new QueryConstructor().updateCuratorInGroupQuery(groupName, curatorName);
                DBConnector.getDbConnector().execute(sqlQuery);
                frame.dispose();
                try {
                    new Application().getRepresentationData();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        grid.add(label);
        grid.add(groupCombobox);
        grid.add(currentCuratorLabel);
        grid.add(curatorComboBox);
        grid.add(changeCuratorButton);

        frame.setContentPane(grid);
        frame.setSize(500, 200);
        frame.setVisible(true);
    }


}
