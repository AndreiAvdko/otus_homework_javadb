package ru.otus.application;

import ru.otus.db.DBConnector;
import ru.otus.entity.Entity;
import ru.otus.entity.Group;
import ru.otus.query.QueryConstructor;
import ru.otus.utils.TableCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class VisualWindow extends JFrame
{
    // Данные для таблиц
    static Map <String, JTable> tableList = new HashMap<>();

    public VisualWindow() {
        super("Все таблицы");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();

        // Добавление в главное меню выпадающих пунктов меню
        menuBar.add(createFileMenu());
        // Подключаем меню к интерфейсу приложения
        setJMenuBar(menuBar);
        // Вывод окна на экран
        setSize(500, 400);
        setVisible(true);
    }

    public VisualWindow addTable(JTable table, String title) {
        tableList.put(title, table);
        return this;
    }

    public void showWindow() {
        Box contents = new Box(BoxLayout.Y_AXIS);
        contents.setBackground(Color.WHITE);
        for (String tableName : tableList.keySet()) {
            contents.add(new Label(tableName));
            contents.add(new JScrollPane(tableList.get(tableName)));
        }
        setContentPane(contents);
        setSize(500, 400);
        setVisible(true);
    }

    private JMenu createFileMenu()
    {
        // Создание выпадающего меню
        JMenu tableActions = new JMenu("| Действия с таблицами |");
        JMenuItem studentsInfoWithGroupAndCurator = new JMenuItem("Информация о студентах (с названием группы и именем куратора)");
        JMenuItem showManWomanStudents = new JMenuItem("Вывести студенток | студентов");
        JMenuItem changeGroupCurator = new JMenuItem("Обновить данные по группе сменив куратора");
        JMenuItem showGroupWithCurator = new JMenuItem("Вывести список групп с их кураторами");
        JMenuItem showStudentFromGroup = new JMenuItem("Вывести на экран студентов из определенной группы");

        tableActions.add(studentsInfoWithGroupAndCurator);
        tableActions.add(showManWomanStudents);
        tableActions.add(changeGroupCurator);
        tableActions.add(showGroupWithCurator);
        tableActions.add(showStudentFromGroup);

        studentsInfoWithGroupAndCurator.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String sqlQuery = "SELECT * FROM \"Student\" JOIN \"Group\" ON id_group = \"Group\".id " +
                        "JOIN \"Curator\" ON \"Group\".id_curator = \"Curator\".id;";

                ResultSet resultSet = DBConnector.getDbConnector().executeQuery(sqlQuery);
                JTable table = null;
                try {
                    table = TableCreator.convertResultSetToJTable(resultSet);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Box contents = new Box(BoxLayout.Y_AXIS);
                JFrame frame = new JFrame();
                frame.setSize(500, 400);
                contents.add(new JLabel(sqlQuery));
                contents.add(new JScrollPane(table));
                frame.setContentPane(contents);
                frame.setVisible(true);
            }
        });

        showManWomanStudents.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Map<String, String> whereFieldEqualVal = new HashMap<>() {{
                    put("sex", "Ж");
                }};
                JTable table;
                String sqlQuery = new QueryConstructor().sortByConditionWhereFieldsEqualValues("Student",
                                                                                                whereFieldEqualVal);
                try {
                    table = TableCreator.convertResultSetToJTable(DBConnector.getDbConnector()
                                                                                    .executeQuery(sqlQuery));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Box contents = new Box(BoxLayout.Y_AXIS);
                JFrame frame = new JFrame();
                frame.setSize(500, 400);
                contents.add(new JLabel(sqlQuery));
                contents.add(new JScrollPane(table));
                frame.setContentPane(contents);
                frame.setVisible(true);
            }
        });

        changeGroupCurator.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println ("Обновить данные по группе сменив куратора");


            }
        });

        showGroupWithCurator.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String sqlQuery = "SELECT * FROM \"Group\" join \"Curator\" ON id_curator = \"Curator\".id;";
                ResultSet resultSet = DBConnector.getDbConnector().executeQuery(sqlQuery);
                JTable table = null;
                try {
                    table = TableCreator.convertResultSetToJTable(resultSet);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Box contents = new Box(BoxLayout.Y_AXIS);
                JFrame frame = new JFrame();
                frame.setSize(500, 400);
                contents.add(new JLabel(sqlQuery));
                contents.add(new JScrollPane(table));
                frame.setContentPane(contents);
                frame.setVisible(true);
            }
        });

        showStudentFromGroup.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                List<Entity> groups = new LinkedList<>();
                try {
                    groups = new Group().getAllEntity();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                String [] groupNames = groups.stream().map(x -> ((Group)x).getName()).toArray(String[]::new);
                new ComboBoxWindow(groupNames);

            }
        });
        return tableActions;
    }
}
