package ru.otus.application;

import javax.swing.*;

public class SqlResultWindow extends JFrame {

    SqlResultWindow(String sqlQuery, JTable table) {
        super("Результат выполнения запроса");
        Box contents = new Box(BoxLayout.Y_AXIS);
        JFrame frame = new JFrame();
        frame.setSize(500, 400);
        contents.add(new JLabel(sqlQuery));
        contents.add(new JScrollPane(table));
        frame.setContentPane(contents);
        frame.setVisible(true);
    }
}
