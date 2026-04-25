package com.jcorner.ui.manager;

import com.jcorner.ui.*;
import com.jcorner.ui.busboy.BusboyDashboardPanel;
import com.jcorner.ui.cook.CookDashboardPanel;
import com.jcorner.ui.waiter.WaiterDashboardPanel;

import javax.swing.*;
import java.awt.*;

public class ManagerDashboardPanel extends JPanel {

    public ManagerDashboardPanel() {
        this(1);
    }

    public ManagerDashboardPanel(int page) {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("MANAGER DASHBOARD (page " + page + "/2)", null), BorderLayout.NORTH);

        JPanel grid = UIStyle.panel(new GridLayout(2, 2, 20, 20));
        grid.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        if (page == 1) {
            grid.add(bigBtn("MANAGE<br>EMPLOYEES",
                    () -> AppFrame.instance().navigate(new ManageEmployeesPanel())));
            grid.add(bigBtn("VIEW<br>REPORTS",
                    () -> AppFrame.instance().navigate(new ReportsPanel())));
            grid.add(bigBtn("INVENTORY",
                    () -> AppFrame.instance().navigate(new InventoryPanel())));
            grid.add(bigBtn("CLOCK<br>IN/OUT",
                    () -> AppFrame.instance().navigate(new ClockPanel())));
        } else {
            grid.add(bigBtn("VIEW WAITER<br>DASHBOARD",
                    () -> AppFrame.instance().navigate(new WaiterDashboardPanel())));
            grid.add(bigBtn("VIEW KITCHEN<br>DASHBOARD",
                    () -> AppFrame.instance().navigate(new CookDashboardPanel())));
            grid.add(bigBtn("VIEW BUSBOY<br>DASHBOARD",
                    () -> AppFrame.instance().navigate(new BusboyDashboardPanel())));
            grid.add(bigBtn("VIEW REFUND<br>REQUESTS",
                    () -> AppFrame.instance().navigate(new RefundRequestsPanel())));
        }
        add(grid, BorderLayout.CENTER);

        JPanel south = UIStyle.panel(new FlowLayout(FlowLayout.RIGHT, 12, 8));
        JButton flip = UIStyle.button(page == 1 ? "NEXT >" : "< PREV");
        flip.addActionListener(e -> AppFrame.instance().navigate(
                new ManagerDashboardPanel(page == 1 ? 2 : 1)));
        south.add(flip);
        add(south, BorderLayout.SOUTH);
    }

    private JButton bigBtn(String html, Runnable action) {
        JButton b = UIStyle.button("<html><center>" + html + "</center></html>");
        b.setFont(UIStyle.HEADER);
        b.addActionListener(e -> action.run());
        return b;
    }
}
