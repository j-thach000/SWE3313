package com.jcorner.ui;

import com.jcorner.service.Session;
import com.jcorner.ui.waiter.WaiterDashboardPanel;
import com.jcorner.ui.cook.CookDashboardPanel;
import com.jcorner.ui.busboy.BusboyDashboardPanel;
import com.jcorner.ui.manager.ManagerDashboardPanel;

import javax.swing.*;
import java.awt.*;

// top bar w/ controls
public class HeaderBar extends JPanel {

    public HeaderBar(String titleText, Runnable backAction) {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel title = UIStyle.label(titleText, UIStyle.TITLE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.CENTER);

        JPanel right = UIStyle.panel(new FlowLayout(FlowLayout.RIGHT, 8, 0));

        if (backAction != null) {
            JButton back = UIStyle.button("BACK");
            back.addActionListener(e -> backAction.run());
            right.add(back);
        }

        if (Session.get().getCurrentUser() != null) {
            JButton home = UIStyle.button("HOME");
            home.addActionListener(e -> navigateHome());
            right.add(home);

            JButton logout = UIStyle.button("LOG OUT");
            logout.addActionListener(e -> {
                com.jcorner.service.AuthService.logout();
                AppFrame.instance().navigate(new LoginPanel());
            });
            right.add(logout);
        }

        add(right, BorderLayout.EAST);
    }

    public static void navigateHome() {
        if (Session.get().getCurrentUser() == null) {
            AppFrame.instance().navigate(new LoginPanel());
            return;
        }
        switch (Session.get().getCurrentUser().getRole()) {
            case WAITER  -> AppFrame.instance().navigate(new WaiterDashboardPanel());
            case COOK    -> AppFrame.instance().navigate(new CookDashboardPanel());
            case BUSBOY  -> AppFrame.instance().navigate(new BusboyDashboardPanel());
            case MANAGER -> AppFrame.instance().navigate(new ManagerDashboardPanel());
        }
    }
}
