package com.jcorner.ui.manager;

import com.jcorner.ui.HeaderBar;
import com.jcorner.ui.UIStyle;

import javax.swing.*;
import java.awt.*;

public class ManagerDashboardPanel extends JPanel {
    public ManagerDashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("MANAGER DASHBOARD", null), BorderLayout.NORTH);
        add(UIStyle.title("Manager dashboard - coming soon"), BorderLayout.CENTER);
    }
}