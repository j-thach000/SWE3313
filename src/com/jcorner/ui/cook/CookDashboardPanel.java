package com.jcorner.ui.cook;

import com.jcorner.ui.HeaderBar;
import com.jcorner.ui.UIStyle;

import javax.swing.*;
import java.awt.*;

public class CookDashboardPanel extends JPanel {
    public CookDashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("COOK DASHBOARD", null), BorderLayout.NORTH);
        add(UIStyle.title("Cook dashboard - coming soon"), BorderLayout.CENTER);
    }
}