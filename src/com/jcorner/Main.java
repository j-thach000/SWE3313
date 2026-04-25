package com.jcorner;

import com.jcorner.data.DataStore;
import com.jcorner.data.Seeder;
import com.jcorner.service.Session;
import com.jcorner.ui.AppFrame;
import com.jcorner.ui.ClockPanel;
import com.jcorner.ui.LoginPanel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Seeder.seed();
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            AppFrame frame = new AppFrame();
            frame.navigate(new LoginPanel());
            frame.setVisible(true);
        });
    }
}