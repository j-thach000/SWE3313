package com.jcorner.ui.waiter;

import com.jcorner.ui.*;

import javax.swing.*;
import java.awt.*;

public class WaiterDashboardPanel extends JPanel {

    public WaiterDashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("WAITER DASHBOARD", null), BorderLayout.NORTH);

        JPanel grid = UIStyle.panel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20, 20, 20, 20);

        c.gridx = 0; c.gridy = 0;
        JButton viewTables = UIStyle.bigButton("<html><center>VIEW<br>ASSIGNED<br>TABLES</center></html>");
        viewTables.addActionListener(e -> AppFrame.instance().navigate(new AssignedTablesPanel()));
        grid.add(viewTables, c);

        c.gridx = 1;
        JButton viewMenu = UIStyle.bigButton("<html><center>VIEW<br>MENU</center></html>");
        viewMenu.addActionListener(e -> AppFrame.instance().navigate(new MenuViewPanel()));
        grid.add(viewMenu, c);

        c.gridx = 2;
        JButton clock = UIStyle.bigButton("<html><center>CLOCK<br>IN/OUT</center></html>");
        clock.addActionListener(e -> AppFrame.instance().navigate(new ClockPanel()));
        grid.add(clock, c);

        add(grid, BorderLayout.CENTER);
    }
}
