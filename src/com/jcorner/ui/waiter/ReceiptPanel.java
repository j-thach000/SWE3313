package com.jcorner.ui.waiter;

import com.jcorner.data.DataStore;
import com.jcorner.model.FoodOrder;
import com.jcorner.model.OrderItem;
import com.jcorner.service.Session;
import com.jcorner.ui.AppFrame;
import com.jcorner.ui.HeaderBar;
import com.jcorner.ui.UIStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;
import java.time.format.DateTimeFormatter;

public class ReceiptPanel extends JPanel {

    private final JTextArea area = new JTextArea();

    public ReceiptPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("PRINT RECEIPT", HeaderBar::navigateHome), BorderLayout.NORTH);

        String orderID= Session.get().getSelectedOrderID();
        FoodOrder order = DataStore.get().orders().get(orderID);
        area.setText(formatReceipt(order));
        area.setFont(UIStyle.MONO);
        area.setEditable(false);
        area.setBackground(Color.WHITE);
        area.setForeground(Color.BLACK);
        area.setBorder(UIStyle.padding(20));
        add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel south = UIStyle.panel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        JButton print = UIStyle.button("PRINT RECEIPT");
        print.addActionListener(e -> {
            try {
                area.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(this, "Printing failed: " + ex.getMessage());
            }
        });
        south.add(print);
        JButton done = UIStyle.button("DONE");
        done.addActionListener(e -> HeaderBar.navigateHome());
        south.add(done);
        add(south, BorderLayout.SOUTH);
    }

    private String formatReceipt(FoodOrder order) {
        StringBuilder sb = new StringBuilder();
        String line = "-".repeat(46);
        sb.append(line).append('\n');
        sb.append("          J'S CORNER RESTAURANT\n");
        sb.append("             jscorner.com\n");
        sb.append("         680 Arntson Dr., Marietta, GA\n");
        sb.append("              (470) 555-1212\n");
        sb.append(line).append('\n');
        sb.append(String.format("Receipt No: %s%n", order.getOrderID()));
        sb.append(String.format("Date: %s   Time: %s%n",
                order.getOrderTime().format(DateTimeFormatter.ISO_LOCAL_DATE),
                order.getOrderTime().format(DateTimeFormatter.ofPattern("hh:mm a"))));
        sb.append(String.format("Waiter ID: %s%n", order.getServerID()));
        sb.append(String.format("Table: %s      Customer: Walk-in%n", order.getTableID()));
        sb.append(line).append('\n');
        sb.append(String.format("%-24s %4s %8s %8s%n", "Item", "Qty", "Price", "Total"));
        sb.append(line).append('\n');
        for (OrderItem i : order.getItems()) {
            sb.append(String.format("%-24s %4d %8.2f %8.2f%n",
                    truncate(i.getMenuItem().getName(), 24),
                    i.getQuantity(),
                    i.getMenuItem().getPrice(),
                    i.getSubtotal()));
        }
        sb.append(line).append('\n');
        sb.append(String.format("%40s%n", "Subtotal:    " + UIStyle.money(order.getSubtotal())));
        sb.append(String.format("%40s%n", "Sales Tax (8%): " + UIStyle.money(order.getTax())));
        sb.append(line).append('\n');
        sb.append(String.format("%40s%n", "TOTAL: " + UIStyle.money(order.getGrandTotal())));
        sb.append(String.format("Payment Method: %s%n",
                order.getPaymentMethod() == null ? "-" : order.getPaymentMethod()));
        sb.append(line).append('\n');
        sb.append("         Thank you for dining with us!\n");
        sb.append(line).append('\n');
        return sb.toString();
    }

    private String truncate(String s, int max) {
        return s.length() <= max ? s : s.substring(0, max);
    }
}
