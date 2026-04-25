package com.jcorner.ui.busboy;

import com.jcorner.ui.HeaderBar;
import com.jcorner.ui.UIStyle;
import com.jcorner.ui.AppFrame;
import com.jcorner.ui.ClockPanel;
import com.jcorner.ui.busboy.FloorStatusPanel;

import javax.swing.*;
import java.awt.*;

public class BusboyDashboardPanel extends JPanel {

    public BusboyDashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("BUSBOY DASHBOARD", null), BorderLayout.NORTH);

        JPanel grid = UIStyle.panel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20, 20, 20, 20);

        c.gridx = 0; c.gridy = 0;
        JButton floor = UIStyle.bigButton("<html><center>EDIT FLOOR<br>STATUS</center></html>");
        floor.addActionListener(e -> AppFrame.instance().navigate(new FloorStatusPanel()));
        grid.add(floor, c);

        c.gridx = 1;
        JButton clock = UIStyle.bigButton("<html><center>CLOCK<br>IN/OUT</center></html>");
        clock.addActionListener(e -> AppFrame.instance().navigate(new ClockPanel()));
        grid.add(clock, c);

        add(grid, BorderLayout.CENTER);
    }
}