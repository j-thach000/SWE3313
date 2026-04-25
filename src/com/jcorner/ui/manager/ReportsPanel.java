package com.jcorner.ui.manager;

import com.jcorner.service.ReportService;
import com.jcorner.ui.HeaderBar;
import com.jcorner.ui.UIStyle;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Map;

public class ReportsPanel extends JPanel {

    private final JTabbedPane tabs = new JTabbedPane();

    public ReportsPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("REPORTS", HeaderBar::navigateHome), BorderLayout.NORTH);

        tabs.addTab("Daily Summary", dailySummaryTab());
        tabs.addTab("Revenue by Item", textTab(formatRevenue(ReportService.revenueByItem())));
        tabs.addTab("Item Popularity", textTab(formatPopularity(ReportService.itemPopularity())));
        tabs.addTab("Efficiency", efficiencyTab());
        add(tabs, BorderLayout.CENTER);
    }

    private JComponent dailySummaryTab() {
        ReportService.DailySummary s = ReportService.dailySummary();
        StringBuilder sb = new StringBuilder();
        String line = "-".repeat(44);
        sb.append(line).append('\n');
        sb.append("         DAILY SALES REPORT\n");
        sb.append(line).append('\n');
        sb.append("Date: ").append(LocalDate.now()).append('\n');
        sb.append("Total Orders:  ").append(s.totalOrders()).append('\n');
        sb.append("Total Revenue: ").append(UIStyle.money(s.totalRevenue())).append('\n');
        sb.append("Tax Collected: ").append(UIStyle.money(s.taxCollected())).append('\n');
        sb.append(line).append('\n');
        sb.append("Top Selling Items:\n");
        int i = 1;
        for (Map.Entry<String, Integer> e : s.topItems()) {
            sb.append(String.format("%d. %-30s %4d%n", i++, e.getKey(), e.getValue()));
        }
        sb.append(line).append('\n');
        sb.append("Payment Breakdown:\n");
        for (var e : s.paymentBreakdown().entrySet()) {
            sb.append(String.format("  %-6s %s%n", e.getKey() + ":", UIStyle.money(e.getValue())));
        }
        sb.append(line).append('\n');
        sb.append(String.format("Average Order Value: %s%n", UIStyle.money(s.avgOrderValue())));
        sb.append(String.format("Average Prep Time:       %.1f min%n", ReportService.avgPrepTimeMinutes()));
        sb.append(String.format("Average Turnaround Time: %.1f min%n", ReportService.avgTurnaroundMinutes()));
        return textTab(sb.toString());
    }

    private JComponent efficiencyTab() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-24s %8s %12s%n", "Employee", "Orders", "Revenue"));
        sb.append("-".repeat(48)).append('\n');
        for (var e : ReportService.personnelEfficiency().entrySet()) {
            sb.append(String.format("%-24s %8.0f %12s%n",
                    truncate(e.getKey(), 24), e.getValue()[0], UIStyle.money(e.getValue()[1])));
        }
        return textTab(sb.toString());
    }

    private String formatRevenue(Map<String, Double> map) {
        if (map.isEmpty()) return "No completed orders yet.";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-32s %12s%n", "Menu Item", "Revenue"));
        sb.append("-".repeat(46)).append('\n');
        for (var e : map.entrySet()) {
            sb.append(String.format("%-32s %12s%n", e.getKey(), UIStyle.money(e.getValue())));
        }
        return sb.toString();
    }

    private String formatPopularity(Map<String, Integer> map) {
        if (map.isEmpty()) return "No completed orders yet.";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-32s %8s%n", "Menu Item", "Sold"));
        sb.append("-".repeat(42)).append('\n');
        for (var e : map.entrySet()) {
            sb.append(String.format("%-32s %8d%n", e.getKey(), e.getValue()));
        }
        return sb.toString();
    }

    private JComponent textTab(String text) {
        JTextArea ta = new JTextArea(text);
        ta.setEditable(false);
        ta.setFont(UIStyle.MONO);
        ta.setBackground(Color.WHITE);
        ta.setForeground(Color.BLACK);
        ta.setBorder(UIStyle.padding(12));
        return new JScrollPane(ta);
    }

    private String truncate(String s, int n) { return s.length() <= n ? s : s.substring(0, n); }
}
