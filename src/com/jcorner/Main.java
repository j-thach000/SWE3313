package com.jcorner;

import com.jcorner.data.Seeder;
import com.jcorner.ui.AppFrame;
import com.jcorner.ui.LoginPanel;

import javax.swing.*;


// J's Corner Restaurant Automation System.
// Team OffLimes - Intro to Software Engineering, Section W03 / SP26

// Starting point that seeds the in-memory data store with the menu, tables,
// and sample employees from the project spec, then shows the login
// screen.
public class Main {

    public static void main(String[] args) {
        Seeder.seed();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            AppFrame frame = new AppFrame();
            frame.navigate(new LoginPanel());
            frame.setVisible(true);
        });
    }
}
