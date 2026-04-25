package com.jcorner.ui.manager;

import com.jcorner.data.DataStore;
import com.jcorner.model.Employee;
import com.jcorner.service.EmployeeService;
import com.jcorner.ui.AppFrame;
import com.jcorner.ui.HeaderBar;
import com.jcorner.ui.UIStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManageEmployeesPanel extends JPanel {

    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Name", "Role", "Locked", "Assigned Tables"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(model);

    public ManageEmployeesPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("MANAGE EMPLOYEES", HeaderBar::navigateHome), BorderLayout.NORTH);

        loadRows();
        table.setFont(UIStyle.BODY);
        table.setRowHeight(24);
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(UIStyle.padding(10));
        add(sp, BorderLayout.CENTER);

        JPanel south = UIStyle.panel(new FlowLayout(FlowLayout.RIGHT, 8, 8));

        JButton add = UIStyle.button("ADD EMPLOYEE");
        add.addActionListener(e -> AppFrame.instance().navigate(new NewEmployeePanel()));
        south.add(add);

        JButton assign = UIStyle.button("ASSIGN TABLES");
        assign.addActionListener(e -> {
            Employee sel = selected();
            if (sel == null) return;
            AssignTablesPanel.show(sel.getEmployeeID());
        });
        south.add(assign);

        JButton schedule = UIStyle.button("VIEW SCHEDULE");
        schedule.addActionListener(e -> {
            Employee sel = selected();
            if (sel == null) return;
            AppFrame.instance().navigate(new SchedulePanel(sel.getEmployeeID()));
        });
        south.add(schedule);

        JButton unlock = UIStyle.button("UNLOCK");
        unlock.addActionListener(e -> {
            Employee sel = selected();
            if (sel == null) return;
            EmployeeService.unlock(sel.getEmployeeID());
            loadRows();
        });
        south.add(unlock);

        JButton remove = UIStyle.button("REMOVE");
        remove.setBackground(UIStyle.DIRTY);
        remove.addActionListener(e -> {
            Employee sel = selected();
            if (sel == null) return;
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Remove employee " + sel.getFullName() + "?",
                    "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                EmployeeService.delete(sel.getEmployeeID());
                loadRows();
            }
        });
        south.add(remove);

        add(south, BorderLayout.SOUTH);
    }

    private Employee selected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select an employee first.");
            return null;
        }
        String id = (String) model.getValueAt(row, 0);
        return DataStore.get().employees().get(id);
    }

    private void loadRows() {
        model.setRowCount(0);
        for (Employee e : DataStore.get().employees().values()) {
            model.addRow(new Object[]{
                    e.getEmployeeID(),
                    e.getFullName(),
                    e.getRole().name(),
                    e.isLocked() ? "YES" : "no",
                    String.join(",", e.getAssignedTableIDs())
            });
        }
    }
}
