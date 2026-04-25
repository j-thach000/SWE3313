package com.jcorner.ui.waiter;

import com.jcorner.data.DataStore;
import com.jcorner.model.MenuCategory;
import com.jcorner.model.MenuItem;
import com.jcorner.ui.AppFrame;
import com.jcorner.ui.HeaderBar;
import com.jcorner.ui.UIStyle;

import javax.swing.*;
import java.awt.*;

public class MenuViewPanel extends JPanel {

    public MenuViewPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("MENU", HeaderBar::navigateHome), BorderLayout.NORTH);

        JPanel content = UIStyle.panel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(UIStyle.padding(20));

        for (MenuCategory cat : DataStore.get().categoriesSorted()) {
            JLabel catLabel = UIStyle.label(cat.getName().toUpperCase(), UIStyle.HEADER);
            catLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            catLabel.setBorder(BorderFactory.createEmptyBorder(12, 0, 6, 0));
            content.add(catLabel);
            for (MenuItem mi : cat.getItems()) {
                String line = String.format("  %-32s %s", mi.getName(), UIStyle.money(mi.getPrice()));
                JLabel itemLabel = UIStyle.label(line, UIStyle.MONO);
                itemLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                if (!mi.isAvailable()) itemLabel.setForeground(Color.LIGHT_GRAY);
                content.add(itemLabel);
                if (mi.getDescription() != null && !mi.getDescription().isEmpty()) {
                    JLabel desc = UIStyle.label("    " + mi.getDescription(), UIStyle.BODY);
                    desc.setForeground(new Color(0xDDDDDD));
                    desc.setAlignmentX(Component.LEFT_ALIGNMENT);
                    content.add(desc);
                }
            }
        }

        JScrollPane sp = new JScrollPane(content);
        sp.getViewport().setBackground(UIStyle.BG);
        sp.setBorder(null);
        add(sp, BorderLayout.CENTER);
    }
}
