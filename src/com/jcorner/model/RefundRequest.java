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

    public RefundRequest(String orderID, String requestedBy, double amount) {
        this.refundID = nextID++;
        this.orderID = orderID;
        this.requestedBy = requestedBy;
        this.amount = amount;
        this.requestedAt = LocalDateTime.now();
        this.status = RefundStatus.PENDING;
    }
}