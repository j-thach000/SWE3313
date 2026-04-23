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

    public long getRefundID() { return refundID; }
    public String getOrderID() { return orderID; }
    public String getRequestedBy() { return requestedBy; }
    public double getAmount() { return amount; }
    public LocalDateTime getRequestedAt() { return requestedAt; }
    public RefundStatus getStatus() { return status; }
    public void setStatus(RefundStatus status) { this.status = status; }
    public String getApprovedBy() { return approvedBy; }
    public void setApprovedBy(String approvedBy) { this.approvedBy = approvedBy; }
    public LocalDateTime getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(LocalDateTime resolvedAt) { this.resolvedAt = resolvedAt; }
}