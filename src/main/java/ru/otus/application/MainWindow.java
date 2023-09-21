package ru.otus.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
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
        menuBar.add(new CustomMenuBar());
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
}
