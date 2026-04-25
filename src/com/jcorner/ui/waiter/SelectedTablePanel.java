package com.jcorner.ui.waiter;

import com.jcorner.data.DataStore;
import com.jcorner.model.FoodOrder;
import com.jcorner.model.RestaurantTable;
import com.jcorner.service.OrderService;
import com.jcorner.service.Session;
import com.jcorner.ui.AppFrame;
import com.jcorner.ui.HeaderBar;
import com.jcorner.ui.UIStyle;

import javax.swing.*;
import java.awt.*;


public class SelectedTablePanel extends JPanel {

    public SelectedTablePanel() {
        String tableID = Session.get().getSelectedTableID();
        RestaurantTable table = DataStore.get().tables().get(tableID);

        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("SELECTED TABLE", () ->
                AppFrame.instance().navigate(new AssignedTablesPanel())), BorderLayout.NORTH);

        // Ensure an order exists once the waiter starts interacting
        FoodOrder order = DataStore.get().activeOrderForTable(tableID);
        if (order == null) {
            order = OrderService.openOrder(tableID, Session.get().getCurrentUser().getEmployeeID(), 1);
        }
        Session.get().setSelectedOrderID(order.getOrderID());

        JPanel main = UIStyle.panel(new BorderLayout());
        main.setBorder(UIStyle.padding(30));

        // Info panel on the right
        JPanel info = UIStyle.panel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.add(UIStyle.label("TABLE ID: " + tableID, UIStyle.HEADER));
        info.add(Box.createVerticalStrut(8));
        info.add(UIStyle.label("ORDER: " + order.getOrderID(), UIStyle.BODY));
        info.add(Box.createVerticalStrut(8));

        JPanel guestRow = UIStyle.panel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        guestRow.add(UIStyle.label("GUESTS:", UIStyle.BODY));
        JSpinner guestSpinner = new JSpinner(new SpinnerNumberModel(order.getGuestCount(), 1, 4, 1));
        FoodOrder orderRef = order;
        guestSpinner.addChangeListener(e -> {
            int g = (Integer) guestSpinner.getValue();
            orderRef.setGuestCount(g);
            table.setCurrentGuestCount(g);
        });
        guestRow.add(guestSpinner);
        info.add(guestRow);

        info.add(Box.createVerticalStrut(16));
        info.add(UIStyle.label("ITEMS: " + order.getItems().size(), UIStyle.BODY));
        info.add(UIStyle.label("SUBTOTAL: " + UIStyle.money(order.getSubtotal()), UIStyle.BODY));

        JButton checkout = UIStyle.button("CHECKOUT");
        checkout.addActionListener(e -> AppFrame.instance().navigate(new PaymentPanel()));
        info.add(Box.createVerticalStrut(20));
        info.add(checkout);

        JButton refund = UIStyle.button("REQUEST REFUND");
        refund.addActionListener(e -> {
            OrderService.requestRefund(orderRef.getOrderID(),
                    Session.get().getCurrentUser().getEmployeeID(), orderRef.getGrandTotal());
            JOptionPane.showMessageDialog(this, "Refund request sent to manager.");
        });
        info.add(Box.createVerticalStrut(8));
        info.add(refund);

        main.add(info, BorderLayout.EAST);

        // Table layout with 4 seats in center (diagram from design doc)
        JPanel diagram = UIStyle.panel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);

        c.gridx = 1; c.gridy = 0;
        diagram.add(seatButton("N (Seat 1)", 1), c);

        c.gridx = 0; c.gridy = 1;
        diagram.add(seatButton("W (Seat 2)", 2), c);

        // Center - the table itself
        c.gridx = 1; c.gridy = 1;
        JLabel tableBlock = new JLabel("TABLE");
        tableBlock.setHorizontalAlignment(SwingConstants.CENTER);
        tableBlock.setPreferredSize(new Dimension(180, 220));
        tableBlock.setOpaque(true);
        tableBlock.setBackground(UIStyle.OCCUPIED);
        tableBlock.setForeground(Color.BLACK);
        tableBlock.setFont(UIStyle.TITLE);
        diagram.add(tableBlock, c);

        c.gridx = 2; c.gridy = 1;
        diagram.add(seatButton("E (Seat 3)", 3), c);

        c.gridx = 1; c.gridy = 2;
        diagram.add(seatButton("S (Seat 4)", 4), c);

        main.add(diagram, BorderLayout.CENTER);
        add(main, BorderLayout.CENTER);
    }

    private JButton seatButton(String label, int seat) {
        JButton b = UIStyle.button("<html><center>TAKE ORDER<br>" + label + "</center></html>");
        b.setBackground(UIStyle.ACCENT);
        b.setForeground(Color.BLACK);
        b.setPreferredSize(new Dimension(160, 70));
        b.addActionListener(e -> {
            Session.get().setSelectedSeat(seat);
            AppFrame.instance().navigate(new TakeOrderPanel());
        });
        return b;
    }
}
