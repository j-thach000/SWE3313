package com.jcorner.ui.manager;

import com.jcorner.data.DataStore;
import com.jcorner.model.InventoryItem;
import com.jcorner.ui.HeaderBar;
import com.jcorner.ui.UIStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InventoryPanel extends JPanel {

    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Item", "Qty", "Unit", "Status"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return c == 2; }
    };
    private final JTable table = new JTable(model);

    public InventoryPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("INVENTORY", HeaderBar::navigateHome), BorderLayout.NORTH);

        loadRows();
        table.setFont(UIStyle.BODY);
        table.setRowHeight(22);
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(UIStyle.padding(10));
        add(sp, BorderLayout.CENTER);

        JPanel south = UIStyle.panel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        JButton save = UIStyle.button("SAVE QUANTITIES");
        save.addActionListener(e -> saveQuantities());
        south.add(save);
        add(south, BorderLayout.SOUTH);
    }

    private void loadRows() {
        model.setRowCount(0);
        for (InventoryItem i : DataStore.get().inventory().values()) {
            model.addRow(new Object[]{
                    i.getItemID(), i.getName(), i.getQuantity(), i.getUnit(),
                    i.getStockStatus().name()
            });
        }
    }

    private void saveQuantities() {
        for (int row = 0; row < model.getRowCount(); row++) {
            String id = (String) model.getValueAt(row, 0);
            InventoryItem i = DataStore.get().inventory().get(id);
            if (i == null) continue;
            Object qty = model.getValueAt(row, 2);
            try {
                int q = (qty instanceof Integer ii) ? ii : Integer.parseInt(qty.toString());
                i.update(q);
            } catch (NumberFormatException ignored) { /* leave unchanged */ }
        }
        loadRows();
        JOptionPane.showMessageDialog(this, "Inventory updated.");
    }
}
