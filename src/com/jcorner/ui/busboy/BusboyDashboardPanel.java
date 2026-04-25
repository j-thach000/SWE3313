package com.jcorner.ui.busboy;

import com.jcorner.ui.HeaderBar;
import com.jcorner.ui.UIStyle;

import javax.swing.*;
import java.awt.*;

public class BusboyDashboardPanel extends JPanel {
    public BusboyDashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("BUSBOY DASHBOARD", null), BorderLayout.NORTH);
        add(UIStyle.title("Busboy dashboard - coming soon"), BorderLayout.CENTER);
    }
}