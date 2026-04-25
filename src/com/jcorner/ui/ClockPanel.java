package com.jcorner.ui;

import com.jcorner.model.ShiftRecord;
import com.jcorner.data.DataStore;
import com.jcorner.service.ShiftService;
import com.jcorner.service.Session;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClockPanel extends JPanel {

    private final JLabel clockLabel = UIStyle.label("", UIStyle.HEADER);
    private final JLabel totalLabel = UIStyle.label("TOTAL TIME WORKED: 00:00:00", UIStyle.HEADER);
    private final Timer ticker;

    public ClockPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("CLOCK IN / OUT", HeaderBar::navigateHome), BorderLayout.NORTH);

        JPanel center = UIStyle.panel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);

        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        center.add(clockLabel, c);

        c.gridy = 1;
        String empID = Session.get().getCurrentUser().getEmployeeID();
        center.add(UIStyle.label("EMPLOYEE ID: " + empID, UIStyle.HEADER), c);

        c.gridwidth = 1; c.gridy = 2;
        JButton in = UIStyle.button("CLOCK IN");
        in.setBackground(UIStyle.READY);
        in.setForeground(Color.BLACK);
        in.addActionListener(e -> { ShiftService.clockIn(empID); refresh(); });
        center.add(in, c);

        c.gridx = 1;
        JButton out = UIStyle.button("CLOCK OUT");
        out.setBackground(UIStyle.DIRTY);
        out.setForeground(Color.WHITE);
        out.addActionListener(e -> { ShiftService.clockOut(empID); refresh(); });
        center.add(out, c);

        c.gridx = 0; c.gridy = 3; c.gridwidth = 2;
        center.add(totalLabel, c);

        add(center, BorderLayout.CENTER);

        ticker = new Timer(1000, e -> refresh());
        ticker.start();
        refresh();
    }

    @Override public void removeNotify() { super.removeNotify(); ticker.stop(); }

    private void refresh() {
        clockLabel.setText("CURRENT TIME: " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        String empID = Session.get().getCurrentUser().getEmployeeID();
        ShiftRecord active = DataStore.get().activeShiftFor(empID);
        long totalSeconds;
        if (active != null) {
            totalSeconds = Duration.between(active.getClockIn(), LocalDateTime.now()).toSeconds();
        } else {
            // sum of today's completed shifts
            LocalDateTime start = LocalDateTime.now().toLocalDate().atStartOfDay();
            totalSeconds = DataStore.get().shiftRecords().stream()
                    .filter(r -> r.getEmployeeID().equals(empID))
                    .filter(r -> r.getClockIn().isAfter(start))
                    .filter(r -> r.getClockOut() != null)
                    .mapToLong(r -> Duration.between(r.getClockIn(), r.getClockOut()).toSeconds())
                    .sum();
        }
        long h = totalSeconds / 3600;
        long m = (totalSeconds % 3600) / 60;
        long s = totalSeconds % 60;
        totalLabel.setText(String.format("TOTAL TIME WORKED: %02d:%02d:%02d", h, m, s));
    }
}
