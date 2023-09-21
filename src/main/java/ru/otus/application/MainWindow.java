package ru.otus.application;

import ru.otus.db.DBConnector;
import ru.otus.entity.Curator;
import ru.otus.entity.Entity;
import ru.otus.entity.Group;
import ru.otus.query.QueryConstructor;
import ru.otus.utils.TableCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainWindow extends JFrame
{
    static Map <String, JTable> tableList = new HashMap<>();
    JMenuBar menuBar = new JMenuBar();
    Box contents = new Box(BoxLayout.Y_AXIS);
    public MainWindow() {
        super("Все таблицы");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new Application().dropTables();
            }
        });
        menuBar.add(createMenuBar());
        setJMenuBar(menuBar);
        setSize(500, 400);
        setVisible(true);
    }
    public void addTable(JTable table, String title) {
        tableList.put(title, table);
    }
    public void showWindow() {
        contents.setBackground(Color.WHITE);
        for (String tableName : tableList.keySet()) {
            contents.add(new Label(tableName));
            contents.add(new JScrollPane(tableList.get(tableName)));
        }
        setContentPane(contents);
        setSize(500, 400);
        setVisible(true);
    }

    private JMenu createMenuBar()
    {
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
        studentsInfoWithGroupAndCurator.addActionListener(studentInfoListener -> {
            String sqlQuery = "SELECT * FROM \"Student\" JOIN \"Group\" ON id_group = \"Group\".id " +
                    "JOIN \"Curator\" ON \"Group\".id_curator = \"Curator\".id;";

            ResultSet resultSet = DBConnector.getDbConnector().executeQuery(sqlQuery);
            JTable table;
            try {
                table = TableCreator.convertResultSetToJTable(resultSet);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            new SqlResultWindow(sqlQuery, table);
        });

        showManWomanStudents.addActionListener(action -> {
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
            new SqlResultWindow(sqlQuery, table);
        });

        changeGroupCurator.addActionListener(action -> {
            List<Entity> groups;
            List<Entity> curators;
            try {
                groups = new Group().getAllEntity();
                curators = new Curator().getAllEntity();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String [] groupNames = groups.stream().map(x -> ((Group)x).getName()).toArray(String[]::new);
            String [] curatorFioList = curators.stream().map(x -> ((Curator)x).getFio()).toArray(String[]::new);

            new ChangeCuratorGroupWindow(groupNames, curatorFioList);
            dispose();
        });

        showGroupWithCurator.addActionListener(action -> {
            String sqlQuery = "SELECT * FROM \"Group\" join \"Curator\" ON id_curator = \"Curator\".id;";
            ResultSet resultSet = DBConnector.getDbConnector().executeQuery(sqlQuery);
            JTable table = null;
            try {
                table = TableCreator.convertResultSetToJTable(resultSet);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            new SqlResultWindow(sqlQuery, table);
        });

        showStudentFromGroup.addActionListener(action -> {
            List<Entity> groups;
            try {
                groups = new Group().getAllEntity();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            String [] groupNames = groups.stream().map(x -> ((Group)x).getName()).toArray(String[]::new);
            new ChooseGroupFilterWindow(groupNames);
        });
        return tableActions;
    }
}
