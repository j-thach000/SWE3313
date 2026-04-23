package com.jcorner.service;

import com.jcorner.data.DataStore;
import com.jcorner.model.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class OrderService {
    public static FoodOrder openOrder(String tableID, String serverID, int guestCount) {
        DataStore ds = DataStore.get();
        FoodOrder existing = ds.activeOrderForTable(tableID);
        if (existing != null) return existing;

        FoodOrder o = new FoodOrder(ds.nextOrderID(), serverID, tableID, guestCount);
        ds.orders().put(o.getOrderID(), o);
        TableService.markOccupied(tableID, guestCount);
        return o;
    }

    // adds an item to the order for a specific seat. Flips status to IN_PROGRESS. 
    public static void addItem(String orderID, MenuItem menuItem, int seat, int qty, String notes) {
        FoodOrder o = DataStore.get().orders().get(orderID);
        if (o == null) return;
        if (!menuItem.isAvailable()) return;
        o.getItems().add(new OrderItem(menuItem, seat, qty, notes));
        if (o.getStatus() == OrderStatus.PENDING) o.setStatus(OrderStatus.IN_PROGRESS);
    }

    // removes a line item from the order. 
    public static void removeItem(String orderID, long orderItemID) {
        FoodOrder o = DataStore.get().orders().get(orderID);
        if (o == null) return;
        o.getItems().removeIf(i -> i.getOrderItemID() == orderItemID);
    }

    // cook marks the order ready; records readyTime and notifies the waiter. 
    public static void markReady(String orderID) {
        FoodOrder o = DataStore.get().orders().get(orderID);
        if (o == null) return;
        if (o.getItems().isEmpty()) return; // design doc: deny ready on empty order
        o.setReadyTime(LocalDateTime.now());
        o.setStatus(OrderStatus.READY);
    }

    // Cook removes a delivered order from the kitchen display. 
    public static void markServed(String orderID) {
        FoodOrder o = DataStore.get().orders().get(orderID);
        if (o == null) return;
        o.setServedTime(LocalDateTime.now());
        // Keep status READY until payment closes it; but for the cook screen
        // the "remove from display" action is purely a display-layer concern.
    }

    // Closes the order after payment; marks table DIRTY. 
    public static void closeOrder(String orderID, String paymentMethod) {
        FoodOrder o = DataStore.get().orders().get(orderID);
        if (o == null) return;
        o.setPaymentMethod(paymentMethod);
        o.setClosedTime(LocalDateTime.now());
        o.setStatus(OrderStatus.SERVED);
        TableService.markDirty(o.getTableID());
    }

    public static void cancelOrder(String orderID) {
        FoodOrder o = DataStore.get().orders().get(orderID);
        if (o == null) return;
        o.setStatus(OrderStatus.CANCELLED);
    }

    // Creates a refund request pending manager approval. 
    public static RefundRequest requestRefund(String orderID, String requestedBy, double amount) {
        RefundRequest r = new RefundRequest(orderID, requestedBy, amount);
        DataStore.get().refundRequests().add(r);
        return r;
    }

    // Orders queued for the kitchen in FIFO order (oldest first). 
    public static List<FoodOrder> kitchenQueue() {
        return DataStore.get().orders().values().stream()
                .filter(o -> o.getStatus() == OrderStatus.PENDING || o.getStatus() == OrderStatus.IN_PROGRESS)
                .sorted(Comparator.comparing(FoodOrder::getOrderTime))
                .toList();
    }

    // Orders that are ready for the waiter to pick up. 
    public static List<FoodOrder> readyOrders() {
        return DataStore.get().orders().values().stream()
                .filter(o -> o.getStatus() == OrderStatus.READY)
                .sorted(Comparator.comparing(FoodOrder::getReadyTime))
                .toList();
    }

    public static int countCompletedToday() {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        return (int) DataStore.get().orders().values().stream()
                .filter(o -> o.getStatus() == OrderStatus.SERVED
                        && o.getClosedTime() != null
                        && o.getClosedTime().isAfter(startOfDay))
                .count();
    }
}
