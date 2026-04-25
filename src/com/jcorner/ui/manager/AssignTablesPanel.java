package com.jcorner.ui.manager;

import com.jcorner.data.DataStore;
import com.jcorner.model.Employee;
import com.jcorner.ui.AppFrame;
import com.jcorner.ui.HeaderBar;
import com.jcorner.ui.UIStyle;

import javax.swing.*;
import java.awt.*;

public class AssignTablesPanel extends JPanel {

    private final String employeeID;

    public static void show(String employeeID) {
        AppFrame.instance().navigate(new AssignTablesPanel(employeeID));
    }

    public AssignTablesPanel(String employeeID) {
        this.employeeID = employeeID;
        Employee emp = DataStore.get().employees().get(employeeID);

        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("ASSIGN TABLES TO " + employeeID,
                () -> AppFrame.instance().navigate(new ManageEmployeesPanel())), BorderLayout.NORTH);

        JPanel grid = UIStyle.panel(new GridBagLayout());
        grid.setBorder(UIStyle.padding(20));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);

        String[] cols = {"A","B","C","D","E","F"};
        for (int i = 0; i < cols.length; i++) {
            c.gridx = i + 1; c.gridy = 0;
            JLabel h = UIStyle.label(cols[i], UIStyle.HEADER);
            h.setHorizontalAlignment(SwingConstants.CENTER);
            grid.add(h, c);
        }

        for (int r = 1; r <= 6; r++) {
            c.gridy = r;
            c.gridx = 0;
            grid.add(UIStyle.label(String.valueOf(r), UIStyle.HEADER), c);
            for (int i = 0; i < cols.length; i++) {
                c.gridx = i + 1;
                String tid = cols[i] + r;
                JToggleButton cell = new JToggleButton(tid, emp.getAssignedTableIDs().contains(tid));
                cell.setPreferredSize(new Dimension(70, 46));
                cell.setFocusPainted(false);
                cell.setForeground(Color.BLACK);
                updateCellColor(cell);
                cell.addActionListener(e -> {
                    if (cell.isSelected()) emp.getAssignedTableIDs().add(tid);
                    else emp.getAssignedTableIDs().remove(tid);
                    updateCellColor(cell);
                });
                grid.add(cell, c);
            }
        }

        add(grid, BorderLayout.CENTER);
    }

    private void updateCellColor(JToggleButton b) {
        b.setBackground(b.isSelected() ? UIStyle.ACCENT : new Color(0x8B5E3C));
        b.setOpaque(true);
        b.setBorderPainted(false);
    }
}
