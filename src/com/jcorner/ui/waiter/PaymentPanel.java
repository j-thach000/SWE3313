package com.jcorner.ui.waiter;

import com.jcorner.data.DataStore;
import com.jcorner.model.FoodOrder;
import com.jcorner.model.OrderItem;
import com.jcorner.service.OrderService;
import com.jcorner.service.Session;
import com.jcorner.ui.AppFrame;
import com.jcorner.ui.HeaderBar;
import com.jcorner.ui.UIStyle;

import javax.swing.*;
import java.awt.*;

public class PaymentPanel extends JPanel {

    public PaymentPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("PAYMENT SCREEN", () ->
                AppFrame.instance().navigate(new SelectedTablePanel())), BorderLayout.NORTH);

        String orderID = Session.get().getSelectedOrderID();
        FoodOrder order = DataStore.get().orders().get(orderID);

        JPanel center = UIStyle.panel(new BorderLayout());
        center.setBorder(UIStyle.padding(20));

        // Left - itemized list
        JPanel itemized = UIStyle.panel();
        itemized.setLayout(new BoxLayout(itemized, BoxLayout.Y_AXIS));
        itemized.add(UIStyle.label("ORDER " + orderID + "  -  TABLE " + order.getTableID(), UIStyle.HEADER));
        itemized.add(Box.createVerticalStrut(10));
        for (int seat = 1; seat <= order.getGuestCount(); seat++) {
            var seatItems = order.itemsForSeat(seat);
            if (seatItems.isEmpty()) continue;
            itemized.add(UIStyle.label("Seat " + seat, UIStyle.HEADER));
            for (OrderItem i : seatItems) {
                String line = String.format("  %-28s x%-2d  %s",
                        i.getMenuItem().getName(), i.getQuantity(), UIStyle.money(i.getSubtotal()));
                itemized.add(UIStyle.label(line, UIStyle.MONO));
            }
            itemized.add(Box.createVerticalStrut(6));
        }
        center.add(new JScrollPane(itemized), BorderLayout.CENTER);

        // Right - totals & payment buttons
        JPanel right = UIStyle.panel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBorder(UIStyle.padding(20));
        right.add(UIStyle.label("SUBTOTAL: " + UIStyle.money(order.getSubtotal()), UIStyle.HEADER));
        right.add(UIStyle.label("TAX (8%): " + UIStyle.money(order.getTax()), UIStyle.BODY));
        right.add(Box.createVerticalStrut(6));
        JLabel total = UIStyle.label("TOTAL: " + UIStyle.money(order.getGrandTotal()), UIStyle.TITLE);
        right.add(total);
        right.add(Box.createVerticalStrut(16));
        right.add(UIStyle.label("SELECT PAYMENT TYPE", UIStyle.HEADER));
        right.add(Box.createVerticalStrut(8));

        JButton card = UIStyle.button("CARD");
        card.addActionListener(e -> finalizePayment(order, "CARD"));
        right.add(card);

        right.add(Box.createVerticalStrut(6));
        JButton cash = UIStyle.button("CASH");
        cash.addActionListener(e -> finalizePayment(order, "CASH"));
        right.add(cash);

        right.add(Box.createVerticalStrut(12));
        JButton cancel = UIStyle.button("CANCEL");
        cancel.addActionListener(e -> AppFrame.instance().navigate(new SelectedTablePanel()));
        right.add(cancel);

        center.add(right, BorderLayout.EAST);
        add(center, BorderLayout.CENTER);
    }

    private void finalizePayment(FoodOrder order, String method) {
        OrderService.closeOrder(order.getOrderID(), method);
        AppFrame.instance().navigate(new ReceiptPanel());
    }
}
