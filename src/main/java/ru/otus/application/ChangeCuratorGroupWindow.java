package ru.otus.application;

import ru.otus.db.DBConnector;
import ru.otus.entity.Group;
import ru.otus.query.QueryConstructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChangeCuratorGroupWindow extends JFrame {
    JLabel currentCuratorLabel = new JLabel();
    JComboBox<String> groupCombobox;
    JComboBox<String> curatorComboBox;
    JPanel grid = new JPanel();
    JFrame frame = new JFrame();
    public ChangeCuratorGroupWindow(String[] groupNames, String[] curatorNames) {
        super("Сменить куратора у группы");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            new Application().getRepresentationData();
            }
        });

        GridLayout layout = new GridLayout(5, 1, 5, 12);
        grid.setLayout(layout);
        grid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel userActionDescription = new JLabel("Выберите название группы и куратора для неё");

        groupCombobox = new JComboBox<>(groupNames);
        curatorComboBox = new JComboBox<>(curatorNames);
        groupCombobox.addActionListener(action -> getCurrentCurator());
        getCurrentCurator();


        JButton changeCuratorButton = new JButton("Сменить куратора");
        changeCuratorButton.addActionListener(action -> changeCuratorAction());

        grid.add(userActionDescription);
        grid.add(groupCombobox);
        grid.add(currentCuratorLabel);
        grid.add(curatorComboBox);
        grid.add(changeCuratorButton);
        frame.setContentPane(grid);
        frame.setSize(500, 200);
        frame.setVisible(true);
    }
    public void changeCuratorAction() {
        String groupName = groupCombobox.getSelectedItem().toString();
        String curatorName = curatorComboBox.getSelectedItem().toString();
        String sqlQuery = new QueryConstructor().updateCuratorInGroupQuery(groupName, curatorName);
        DBConnector.getDbConnector().execute(sqlQuery);
        frame.dispose();
        new Application().getRepresentationData();
    }
    public void getCurrentCurator() {
        currentCuratorLabel.setText("Текущий куратор у этой группы - " + new Group().getGroupCurator(groupCombobox.getSelectedItem().toString()));
    }


}
