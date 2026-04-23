package com.jcorner.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class ShiftRecord {
    private final long recordID;
    private final String employeeID;
    private final LocalDateTime clockIn;
    private LocalDateTime clockOut; // null while shift is active

    private static long nextID = 1;

    public ShiftRecord(String employeeID, LocalDateTime clockIn) {
        this.recordID = nextID++;
        this.employeeID = employeeID;
        this.clockIn = clockIn;
    }
}