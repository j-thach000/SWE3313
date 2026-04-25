package com.jcorner.ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public final class UIStyle {

    public static final Color BG          = new Color(0x5C5C5C);
    public static final Color PANEL       = new Color(0x6B6B6B);
    public static final Color BTN_BG      = new Color(0x1C1C1C);
    public static final Color BTN_FG      = Color.WHITE;
    public static final Color TEXT        = Color.WHITE;
    public static final Color ACCENT      = new Color(0xA8E0E6);

    public static final Color READY       = new Color(0x3BB143);  // green
    public static final Color OCCUPIED    = new Color(0xE8C547);  // yellow
    public static final Color DIRTY       = new Color(0xD8453B);  // red
    public static final Color ERROR       = new Color(0xE74C3C);

    public static final Font TITLE        = new Font("SansSerif", Font.BOLD, 22);
    public static final Font HEADER       = new Font("SansSerif", Font.BOLD, 16);
    public static final Font BODY         = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font MONO         = new Font("Monospaced", Font.PLAIN, 13);

    private UIStyle() {}

    public static JButton button(String text) {
        JButton b = new JButton(text);
        b.setBackground(BTN_BG);
        b.setForeground(BTN_FG);
        b.setFocusPainted(false);
        b.setFont(HEADER);
        b.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        // force opaque painting on macOS LaF
        b.setOpaque(true);
        b.setBorderPainted(false);
        return b;
    }

    public static JButton bigButton(String text) {
        JButton b = button(text);
        b.setPreferredSize(new Dimension(180, 110));
        return b;
    }

    public static JLabel label(String text, Font font) {
        JLabel l = new JLabel(text);
        l.setForeground(TEXT);
        l.setFont(font);
        return l;
    }

    public static JLabel title(String text) {
        JLabel l = label(text, TITLE);
        l.setHorizontalAlignment(SwingConstants.CENTER);
        return l;
    }

    public static JPanel panel() {
        JPanel p = new JPanel();
        p.setBackground(BG);
        return p;
    }

    public static JPanel panel(LayoutManager lm) {
        JPanel p = new JPanel(lm);
        p.setBackground(BG);
        return p;
    }

    public static Border padding(int px) {
        return BorderFactory.createEmptyBorder(px, px, px, px);
    }

    public static Color tableColor(com.jcorner.model.TableStatus s) {
        return switch (s) {
            case READY    -> READY;
            case OCCUPIED -> OCCUPIED;
            case DIRTY    -> DIRTY;
        };
    }

    public static String money(double d) {
        return String.format("$%.2f", d);
    }
}
