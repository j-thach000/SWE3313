package com.jcorner.ui.cook;

import com.jcorner.model.FoodOrder;
import com.jcorner.model.OrderItem;
import com.jcorner.service.OrderService;
import com.jcorner.ui.AppFrame;
import com.jcorner.ui.HeaderBar;
import com.jcorner.ui.UIStyle;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;

public class KitchenQueuePanel extends JPanel {

    private final JPanel list = UIStyle.panel();
    private final Timer refreshTimer;

    public KitchenQueuePanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("KITCHEN SCREEN", HeaderBar::navigateHome), BorderLayout.NORTH);

        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        JScrollPane sp = new JScrollPane(list);
        sp.setBorder(null);
        sp.getViewport().setBackground(UIStyle.BG);
        add(sp, BorderLayout.CENTER);

        refresh();
        refreshTimer = new Timer(3000, e -> refresh());
        refreshTimer.start();
    }

    @Override public void removeNotify() { super.removeNotify(); refreshTimer.stop(); }

    private void refresh() {
        list.removeAll();
        for (FoodOrder o : OrderService.kitchenQueue()) list.add(orderCard(o));
        if (OrderService.kitchenQueue().isEmpty()) {
            list.add(UIStyle.label("  No pending orders.", UIStyle.HEADER));
        }
        list.revalidate();
        list.repaint();
    }

    private JPanel orderCard(FoodOrder o) {
        JPanel card = UIStyle.panel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(8, 12, 8, 12),
                BorderFactory.createLineBorder(Color.BLACK, 2)));
        card.setBackground(UIStyle.PANEL);

        JPanel header = UIStyle.panel(new FlowLayout(FlowLayout.LEFT, 20, 4));
        header.setBackground(UIStyle.PANEL);
        header.add(UIStyle.label("ORDER " + o.getOrderID(), UIStyle.HEADER));
        header.add(UIStyle.label("TABLE " + o.getTableID(), UIStyle.HEADER));
        long secs = Duration.between(o.getOrderTime(), LocalDateTime.now()).toSeconds();
        header.add(UIStyle.label("ELAPSED: " + String.format("%02d:%02d", secs / 60, secs % 60),
                UIStyle.HEADER));
        header.add(UIStyle.label("STATUS: " + o.getStatus(), UIStyle.HEADER));
        card.add(header, BorderLayout.NORTH);

        StringBuilder body = new StringBuilder("<html>");
        for (int seat = 1; seat <= o.getGuestCount(); seat++) {
            var its = o.itemsForSeat(seat);
            if (its.isEmpty()) continue;
            body.append("<b>Seat ").append(seat).append(":</b> ");
            for (int i = 0; i < its.size(); i++) {
                OrderItem it = its.get(i);
                if (i > 0) body.append(", ");
                body.append(it.getQuantity()).append("x ").append(it.getMenuItem().getName());
                if (it.getSpecialInstructions() != null && !it.getSpecialInstructions().isEmpty()) {
                    body.append(" (").append(it.getSpecialInstructions()).append(")");
                }
            }
            body.append("<br>");
        }
        body.append("</html>");
        JLabel items = UIStyle.label(body.toString(), UIStyle.BODY);
        items.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        card.add(items, BorderLayout.CENTER);

        JPanel actions = UIStyle.panel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
        actions.setBackground(UIStyle.PANEL);
        JButton ready = UIStyle.button("MARK READY");
        ready.setBackground(UIStyle.READY);
        ready.setForeground(Color.BLACK);
        ready.addActionListener(e -> { OrderService.markReady(o.getOrderID()); refresh(); });
        actions.add(ready);

        JButton cancel = UIStyle.button("CANCEL ORDER");
        cancel.setBackground(UIStyle.DIRTY);
        cancel.addActionListener(e -> { OrderService.cancelOrder(o.getOrderID()); refresh(); });
        actions.add(cancel);
        card.add(actions, BorderLayout.SOUTH);

        return card;
    }
}
