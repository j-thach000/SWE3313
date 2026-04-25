package com.jcorner.ui;

import javax.swing.*;
import java.awt.*;

// main window w/ each panel being placed in this "frame"
public class AppFrame extends JFrame {

    private static AppFrame instance;
    public static AppFrame instance() { return instance; }

    public AppFrame() {
        super("J's Corner Restaurant - Staff Terminal");
        instance = this;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 720);
        setMinimumSize(new Dimension(960, 640));
        setLocationRelativeTo(null);
        getContentPane().setBackground(UIStyle.BG);
        setLayout(new BorderLayout());
    }

    // swap the content of the frame to a new panel.
    public void navigate(JPanel newPanel) {
        getContentPane().removeAll();
        getContentPane().add(newPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}