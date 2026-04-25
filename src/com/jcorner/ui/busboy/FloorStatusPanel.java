package com.jcorner.ui.busboy;

import com.jcorner.data.DataStore;
import com.jcorner.model.RestaurantTable;
import com.jcorner.model.TableStatus;
import com.jcorner.service.TableService;
import com.jcorner.ui.HeaderBar;
import com.jcorner.ui.UIStyle;

import javax.swing.*;
import java.awt.*;

/**
 * Full 6x6 floor grid. Busboy (and Manager, which reuses this panel)
 * can click any table to cycle through READY -> OCCUPIED -> DIRTY ->
 * READY. The state transition diagram in the requirements doc shows
 * these three states so we cycle through them.
 */
public class FloorStatusPanel extends JPanel {

    public FloorStatusPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("FLOOR STATUS", HeaderBar::navigateHome), BorderLayout.NORTH);

        JPanel grid = UIStyle.panel(new GridBagLayout());
        grid.setBorder(UIStyle.padding(20));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.BOTH;

        // Column headers
        c.gridy = 0;
        String[] cols = {"A","B","C","D","E","F"};
        for (int i = 0; i < cols.length; i++) {
            c.gridx = i + 1;
            JLabel h = UIStyle.label(cols[i], UIStyle.HEADER);
            h.setHorizontalAlignment(SwingConstants.CENTER);
            grid.add(h, c);
        }

        for (int r = 1; r <= 6; r++) {
            c.gridy = r;
            c.gridx = 0;
            JLabel rowLabel = UIStyle.label(String.valueOf(r), UIStyle.HEADER);
            grid.add(rowLabel, c);
            for (int i = 0; i < cols.length; i++) {
                c.gridx = i + 1;
                String tid = cols[i] + r;
                RestaurantTable t = DataStore.get().tables().get(tid);
                JButton cell = new JButton(tid);
                cell.setPreferredSize(new Dimension(80, 60));
                cell.setFocusPainted(false);
                cell.setOpaque(true);
                cell.setBorderPainted(false);
                cell.setFont(UIStyle.HEADER);
                cell.setForeground(Color.BLACK);
                cell.setBackground(UIStyle.tableColor(t.getStatus()));
                cell.addActionListener(e -> {
                    TableService.changeStatus(tid, nextStatus(t.getStatus()));
                    cell.setBackground(UIStyle.tableColor(t.getStatus()));
                });
                grid.add(cell, c);
            }
        }

        add(grid, BorderLayout.CENTER);

        JPanel legend = UIStyle.panel(new FlowLayout(FlowLayout.RIGHT, 12, 6));
        legend.add(legendSwatch("READY", UIStyle.READY));
        legend.add(legendSwatch("OCCUPIED", UIStyle.OCCUPIED));
        legend.add(legendSwatch("DIRTY", UIStyle.DIRTY));
        add(legend, BorderLayout.SOUTH);
    }

    private TableStatus nextStatus(TableStatus s) {
        return switch (s) {
            case READY    -> TableStatus.OCCUPIED;
            case OCCUPIED -> TableStatus.DIRTY;
            case DIRTY    -> TableStatus.READY;
        };
    }

    private JPanel legendSwatch(String text, Color color) {
        JPanel p = UIStyle.panel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        JLabel sw = new JLabel("   ");
        sw.setOpaque(true);
        sw.setBackground(color);
        sw.setPreferredSize(new Dimension(20, 20));
        p.add(sw);
        p.add(UIStyle.label(text, UIStyle.BODY));
        return p;
    }
}
