package com.jcorner.ui.cook;

import com.jcorner.ui.*;

import javax.swing.*;
import java.awt.*;

public class CookDashboardPanel extends JPanel {

    public CookDashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("COOK DASHBOARD", null), BorderLayout.NORTH);

        JPanel grid = UIStyle.panel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20, 20, 20, 20);

        c.gridx = 0; c.gridy = 0;
        JButton q = UIStyle.bigButton("<html><center>VIEW<br>ORDER<br>QUEUE</center></html>");
        q.addActionListener(e -> AppFrame.instance().navigate(new KitchenQueuePanel()));
        grid.add(q, c);

        c.gridx = 1;
        JButton ready = UIStyle.bigButton("<html><center>MARK<br>ORDER<br>READY</center></html>");
        ready.addActionListener(e -> AppFrame.instance().navigate(new OrderReadyPanel()));
        grid.add(ready, c);

        c.gridx = 2;
        JButton clock = UIStyle.bigButton("<html><center>CLOCK<br>IN/OUT</center></html>");
        clock.addActionListener(e -> AppFrame.instance().navigate(new ClockPanel()));
        grid.add(clock, c);

        add(grid, BorderLayout.CENTER);
    }
}
