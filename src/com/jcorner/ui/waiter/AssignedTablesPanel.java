package com.jcorner.ui.waiter;

import com.jcorner.data.DataStore;
import com.jcorner.model.Employee;
import com.jcorner.model.RestaurantTable;
import com.jcorner.service.Session;
import com.jcorner.ui.AppFrame;
import com.jcorner.ui.HeaderBar;
import com.jcorner.ui.UIStyle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AssignedTablesPanel extends JPanel {

    public AssignedTablesPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("ASSIGNED TABLES", HeaderBar::navigateHome), BorderLayout.NORTH);

        Employee me = Session.get().getCurrentUser();
        List<RestaurantTable> mine = new ArrayList<>();
        for (String tid : me.getAssignedTableIDs()) {
            RestaurantTable t = DataStore.get().tables().get(tid);
            if (t != null) mine.add(t);
        }
        mine.sort(Comparator.comparing(RestaurantTable::getTableID));

        JPanel grid = UIStyle.panel(new GridLayout(0, 2, 16, 12));
        grid.setBorder(UIStyle.padding(20));

        int idx = 1;
        for (RestaurantTable t : mine) {
            JPanel row = UIStyle.panel(new FlowLayout(FlowLayout.LEFT, 12, 4));
            row.add(UIStyle.label(idx++ + "", UIStyle.HEADER));
            JButton cell = new JButton("TABLE " + t.getTableID());
            cell.setPreferredSize(new Dimension(160, 50));
            cell.setBackground(UIStyle.tableColor(t.getStatus()));
            cell.setForeground(Color.BLACK);
            cell.setFont(UIStyle.HEADER);
            cell.setFocusPainted(false);
            cell.setOpaque(true);
            cell.setBorderPainted(false);
            cell.addActionListener(e -> {
                Session.get().setSelectedTableID(t.getTableID());
                AppFrame.instance().navigate(new SelectedTablePanel());
            });
            row.add(cell);
            row.add(UIStyle.label(t.getStatus().name(), UIStyle.BODY));
            grid.add(row);
        }

        add(new JScrollPane(grid), BorderLayout.CENTER);

        JPanel legend = UIStyle.panel(new FlowLayout(FlowLayout.RIGHT, 12, 6));
        legend.add(legendSwatch("READY", UIStyle.READY));
        legend.add(legendSwatch("OCCUPIED", UIStyle.OCCUPIED));
        legend.add(legendSwatch("DIRTY", UIStyle.DIRTY));
        add(legend, BorderLayout.SOUTH);
    }

    private JPanel legendSwatch(String text, Color color) {
        JPanel p = UIStyle.panel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        JLabel sw = new JLabel("   ");
        sw.setOpaque(true);
        sw.setBackground(color);
        sw.setPreferredSize(new Dimension(20, 20));
        p.add(sw);
        p.add(UIStyle.label(text, UIStyle.BODY));
        return p;
    }
}
