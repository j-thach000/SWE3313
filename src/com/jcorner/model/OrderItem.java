package com.jcorner.model;

public class OrderItem {
    private final long orderItemID;
    private final MenuItem menuItem;
    private final int seatNumber; // 1..4
    private int quantity;
    private String specialInstructions;

    private static long nextID = 1;

    public OrderItem(MenuItem menuItem, int seatNumber, int quantity, String specialInstructions) {
        this.orderItemId = nextId++;
        this.menuItem = menuItem;
        this.seatNumber = seatNumber;
        this.quantity = quantity;
        this.specialInstructions = specialInstructions;
    }

    
}