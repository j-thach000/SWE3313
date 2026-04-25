package com.jcorner.ui.waiter;

import com.jcorner.data.DataStore;
import com.jcorner.model.FoodOrder;
import com.jcorner.model.MenuCategory;
import com.jcorner.model.MenuItem;
import com.jcorner.model.OrderItem;
import com.jcorner.service.OrderService;
import com.jcorner.service.Session;
import com.jcorner.ui.AppFrame;
import com.jcorner.ui.HeaderBar;
import com.jcorner.ui.UIStyle;

import javax.swing.*;
import java.awt.*;

public class TakeOrderPanel extends JPanel {

    private final JPanel itemsListPanel = UIStyle.panel();

    public TakeOrderPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("TAKE ORDER", () ->
                AppFrame.instance().navigate(new SelectedTablePanel())), BorderLayout.NORTH);

        String orderID = Session.get().getSelectedOrderID();
        int seat = Session.get().getSelectedSeat() == null ? 1 : Session.get().getSelectedSeat();
        FoodOrder order = DataStore.get().orders().get(orderID);

        JPanel center = UIStyle.panel(new BorderLayout());
        center.setBorder(UIStyle.padding(20));

        // Header showing table / seat context
        JPanel ctx = UIStyle.panel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        ctx.add(UIStyle.label("TABLE " + order.getTableID(), UIStyle.HEADER));
        ctx.add(UIStyle.label("SEAT " + seat, UIStyle.HEADER));
        center.add(ctx, BorderLayout.NORTH);

        // Category rows
        JPanel body = UIStyle.panel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        for (MenuCategory cat : DataStore.get().categoriesSorted()) {
            JPanel row = UIStyle.panel(new FlowLayout(FlowLayout.LEFT, 10, 6));
            JLabel catLabel = UIStyle.label(cat.getName().toUpperCase(), UIStyle.HEADER);
            catLabel.setPreferredSize(new Dimension(140, 26));
            row.add(catLabel);

            JComboBox<Object> combo = new JComboBox<>();
            combo.addItem("-- NONE --");
            for (MenuItem mi : cat.getAvailableItems()) combo.addItem(mi);
            combo.setPreferredSize(new Dimension(280, 26));
            row.add(combo);

            JButton addBtn = UIStyle.button("ADD");
            addBtn.addActionListener(e -> {
                Object sel = combo.getSelectedItem();
                if (sel instanceof MenuItem mi) {
                    String notes = JOptionPane.showInputDialog(this,
                            "Special instructions (optional):", "");
                    OrderService.addItem(orderID, mi, seat, 1, notes == null ? "" : notes);
                    combo.setSelectedIndex(0);
                    refreshItemsList(order, seat);
                }
            });
            row.add(addBtn);
            body.add(row);
        }

        center.add(body, BorderLayout.CENTER);

        // Items already in order (for this seat)
        JPanel east = UIStyle.panel(new BorderLayout());
        east.setPreferredSize(new Dimension(320, 0));
        east.add(UIStyle.label("CURRENT ORDER (SEAT " + seat + ")", UIStyle.HEADER), BorderLayout.NORTH);
        itemsListPanel.setLayout(new BoxLayout(itemsListPanel, BoxLayout.Y_AXIS));
        east.add(new JScrollPane(itemsListPanel), BorderLayout.CENTER);

        JButton submit = UIStyle.button("SUBMIT TO KITCHEN");
        submit.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Order sent to kitchen.");
            AppFrame.instance().navigate(new SelectedTablePanel());
        });
        east.add(submit, BorderLayout.SOUTH);
        center.add(east, BorderLayout.EAST);

        add(center, BorderLayout.CENTER);
        refreshItemsList(order, seat);
    }

    private void refreshItemsList(FoodOrder order, int seat) {
        itemsListPanel.removeAll();
        for (OrderItem i : order.itemsForSeat(seat)) {
            JPanel row = UIStyle.panel(new FlowLayout(FlowLayout.LEFT, 4, 2));
            row.add(UIStyle.label(i.getMenuItem().getName()
                    + " x" + i.getQuantity()
                    + "  " + UIStyle.money(i.getSubtotal()), UIStyle.BODY));
            JButton rm = UIStyle.button("X");
            rm.setPreferredSize(new Dimension(42, 24));
            rm.addActionListener(e -> {
                OrderService.removeItem(order.getOrderID(), i.getOrderItemID());
                refreshItemsList(order, seat);
            });
            row.add(rm);
            if (i.getSpecialInstructions() != null && !i.getSpecialInstructions().isEmpty()) {
                JLabel notes = UIStyle.label("   \"" + i.getSpecialInstructions() + "\"", UIStyle.BODY);
                notes.setForeground(new Color(0xDDDDDD));
                row.add(notes);
            }
            itemsListPanel.add(row);
        }
        itemsListPanel.revalidate();
        itemsListPanel.repaint();
    }
}
