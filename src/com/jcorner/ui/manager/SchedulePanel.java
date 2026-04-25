package com.jcorner.ui.manager;

import com.jcorner.data.DataStore;
import com.jcorner.model.Employee;
import com.jcorner.ui.AppFrame;
import com.jcorner.ui.HeaderBar;
import com.jcorner.ui.UIStyle;

import javax.swing.*;
import java.awt.*;

public class SchedulePanel extends JPanel {

    private static final String[] DAYS = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};

    public SchedulePanel(String employeeID) {
        Employee emp = DataStore.get().employees().get(employeeID);
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("SCHEDULE - " + employeeID,
                () -> AppFrame.instance().navigate(new ManageEmployeesPanel())), BorderLayout.NORTH);

        JPanel grid = UIStyle.panel(new GridLayout(2, 7, 4, 4));
        grid.setBorder(UIStyle.padding(40));

        for (String d : DAYS) {
            JLabel h = UIStyle.label(d, UIStyle.HEADER);
            h.setHorizontalAlignment(SwingConstants.CENTER);
            grid.add(h);
        }
        for (int d = 0; d < 7; d++) {
            int day = d;
            JToggleButton cell = new JToggleButton(emp.worksDay(d) ? "X" : "");
            cell.setSelected(emp.worksDay(d));
            cell.setPreferredSize(new Dimension(80, 60));
            cell.setFont(UIStyle.HEADER);
            cell.setForeground(Color.BLACK);
            cell.setBackground(emp.worksDay(d) ? UIStyle.ACCENT : Color.LIGHT_GRAY);
            cell.setOpaque(true);
            cell.setBorderPainted(false);
            cell.addActionListener(e -> {
                emp.setWorksDay(day, cell.isSelected());
                cell.setText(cell.isSelected() ? "X" : "");
                cell.setBackground(cell.isSelected() ? UIStyle.ACCENT : Color.LIGHT_GRAY);
            });
            grid.add(cell);
        }
        add(grid, BorderLayout.CENTER);

        JPanel south = UIStyle.panel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        JButton print = UIStyle.button("PRINT SCHEDULE");
        print.addActionListener(e -> {
            JTextArea ta = new JTextArea();
            StringBuilder sb = new StringBuilder();
            sb.append("Schedule for ").append(emp.getFullName())
                    .append(" (").append(employeeID).append(")\n\n");
            for (int d = 0; d < 7; d++) sb.append(DAYS[d]).append(emp.worksDay(d) ? " X" : " -").append('\n');
            ta.setText(sb.toString());
            try { ta.print(); } catch (java.awt.print.PrinterException ex) { /* ignored */ }
        });
        south.add(print);
        add(south, BorderLayout.SOUTH);
    }
}
