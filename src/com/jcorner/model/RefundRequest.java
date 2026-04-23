package com.jcorner.model;

import java.time.LocalDateTime;

public class RefundRequest {
    private final long refundID;
    private final String orderID;
    private final String requestedBy;      // waiter employeeId
    private final double amount;
    private final LocalDateTime requestedAt;
    private RefundStatus status;
    private String approvedBy;             // manager employeeId (null until approved)
    private LocalDateTime resolvedAt;
    
    private static long nextID = 1;
}