package com.jcorner.service;

import com.jcorner.data.DataStore;
import com.jcorner.model.RefundRequest;
import com.jcorner.model.RefundStatus;

import java.time.LocalDateTime;
import java.util.List;

public class RefundService {

    public static List<RefundRequest> pending() {
        return DataStore.get().refundRequests().stream()
                .filter(r -> r.getStatus() == RefundStatus.PENDING)
                .toList();
    }

    public static void approve(long refundID, String managerID) {
        for (RefundRequest r : DataStore.get().refundRequests()) {
            if (r.getRefundID() == refundID && r.getStatus() == RefundStatus.PENDING) {
                r.setStatus(RefundStatus.APPROVED);
                r.setApprovedBy(managerID);
                r.setResolvedAt(LocalDateTime.now());
                return;
            }
        }
    }

    public static void deny(long refundID, String managerID) {
        for (RefundRequest r : DataStore.get().refundRequests()) {
            if (r.getRefundID() == refundID && r.getStatus() == RefundStatus.PENDING) {
                r.setStatus(RefundStatus.DENIED);
                r.setApprovedBy(managerID);
                r.setResolvedAt(LocalDateTime.now());
                return;
            }
        }
    }
}
