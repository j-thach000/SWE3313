package com.jcorner.service;

import com.jcorner.data.DataStore;
import com.jcorner.model.ShiftRecord;

import java.time.LocalDateTime;

public class ShiftService {

    public static ShiftRecord clockIn(String employeeID) {
        // If already clocked in, return the active record rather than duplicating.
        ShiftRecord active = DataStore.get().activeShiftFor(employeeID);
        if (active != null) return active;

        ShiftRecord r = new ShiftRecord(employeeID, LocalDateTime.now());
        DataStore.get().shiftRecords().add(r);
        return r;
    }

    public static void clockOut(String employeeID) {
        ShiftRecord active = DataStore.get().activeShiftFor(employeeID);
        if (active != null) active.setClockOut(LocalDateTime.now());
    }

    public static double hoursWorkedToday(String employeeID) {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        return DataStore.get().shiftRecords().stream()
                .filter(r -> r.getEmployeeID().equals(employeeID))
                .filter(r -> r.getClockIn().isAfter(startOfDay))
                .mapToDouble(ShiftRecord::getHoursWorked)
                .sum();
    }
}