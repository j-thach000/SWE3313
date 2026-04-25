package com.jcorner.ui.manager;

import com.jcorner.model.RefundRequest;
import com.jcorner.service.RefundService;
import com.jcorner.service.Session;
import com.jcorner.ui.HeaderBar;
import com.jcorner.ui.UIStyle;

import javax.swing.*;
import java.awt.*;

public class RefundRequestsPanel extends JPanel {

    private final JPanel list = UIStyle.panel();

    public RefundRequestsPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("REFUND REQUESTS", HeaderBar::navigateHome), BorderLayout.NORTH);

        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        add(new JScrollPane(list), BorderLayout.CENTER);
        refresh();
    }

    private void refresh() {
        list.removeAll();
        var pending = RefundService.pending();
        if (pending.isEmpty()) {
            list.add(UIStyle.label("  No pending refund requests.", UIStyle.HEADER));
        }
        for (RefundRequest r : pending) list.add(card(r));
        list.revalidate();
        list.repaint();
    }

    private JPanel card(RefundRequest r) {
        JPanel p = UIStyle.panel(new FlowLayout(FlowLayout.LEFT, 16, 6));
        p.setBackground(UIStyle.PANEL);
        p.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        p.add(UIStyle.label("ORDER " + r.getOrderID(), UIStyle.HEADER));
        p.add(UIStyle.label("REQUESTED BY: " + r.getRequestedBy(), UIStyle.BODY));
        p.add(UIStyle.label("AMOUNT: " + UIStyle.money(r.getAmount()), UIStyle.HEADER));

        JButton approve = UIStyle.button("APPROVE");
        approve.setBackground(UIStyle.READY);
        approve.setForeground(Color.BLACK);
        approve.addActionListener(e -> {
            RefundService.approve(r.getRefundID(), Session.get().getCurrentUser().getEmployeeID());
            refresh();
        });
        p.add(approve);

        JButton deny = UIStyle.button("DENY");
        deny.setBackground(UIStyle.DIRTY);
        deny.addActionListener(e -> {
            RefundService.deny(r.getRefundID(), Session.get().getCurrentUser().getEmployeeID());
            refresh();
        });
        p.add(deny);
        return p;
    }
}
