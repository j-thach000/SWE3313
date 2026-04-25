package com.jcorner.ui.waiter;

import com.jcorner.ui.HeaderBar;
import com.jcorner.ui.UIStyle;

import javax.swing.*;
import java.awt.*;

public class WaiterDashboardPanel extends JPanel {
    public WaiterDashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("WAITER DASHBOARD", null), BorderLayout.NORTH);
        add(UIStyle.title("Waiter dashboard - coming soon"), BorderLayout.CENTER);
    }
}