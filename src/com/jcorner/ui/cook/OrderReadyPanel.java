package com.jcorner.ui.cook;

import com.jcorner.model.FoodOrder;
import com.jcorner.service.OrderService;
import com.jcorner.ui.HeaderBar;
import com.jcorner.ui.UIStyle;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;

public class OrderReadyPanel extends JPanel {

    private final JPanel list = UIStyle.panel();
    private final Timer ticker;

    public OrderReadyPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("ORDERS READY FOR PICKUP", HeaderBar::navigateHome), BorderLayout.NORTH);

        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        add(new JScrollPane(list), BorderLayout.CENTER);

        refresh();
        ticker = new Timer(3000, e -> refresh());
        ticker.start();
    }

    @Override public void removeNotify() { super.removeNotify(); ticker.stop(); }

    private void refresh() {
        list.removeAll();
        for (FoodOrder o : OrderService.readyOrders()) list.add(card(o));
        if (OrderService.readyOrders().isEmpty()) {
            list.add(UIStyle.label("  No orders waiting for pickup.", UIStyle.HEADER));
        }
        list.revalidate();
        list.repaint();
    }

    private JPanel card(FoodOrder o) {
        JPanel p = UIStyle.panel(new FlowLayout(FlowLayout.LEFT, 16, 6));
        p.setBackground(UIStyle.PANEL);
        p.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        p.add(UIStyle.label("ORDER " + o.getOrderID(), UIStyle.HEADER));
        p.add(UIStyle.label("TABLE " + o.getTableID(), UIStyle.HEADER));
        long secs = o.getReadyTime() == null ? 0
                : Duration.between(o.getReadyTime(), LocalDateTime.now()).toSeconds();
        p.add(UIStyle.label("READY FOR: " + String.format("%02d:%02d", secs / 60, secs % 60),
                UIStyle.HEADER));
        JButton remove = UIStyle.button("REMOVE FROM DISPLAY");
        remove.addActionListener(e -> { OrderService.markServed(o.getOrderID()); refresh(); });
        p.add(remove);
        return p;
    }
}
