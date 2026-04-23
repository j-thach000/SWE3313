package com.jcorner.model;

public class OrderItem {
    private final long orderItemID;
    private final MenuItem menuItem;
    private final int seatNumber; // 1..4
    private int quantity;
    private String specialInstructions;

    private static long nextID = 1;

    public OrderItem(MenuItem menuItem, int seatNumber, int quantity, String specialInstructions) {
        this.orderItemID = nextID++;
        this.menuItem = menuItem;
        this.seatNumber = seatNumber;
        this.quantity = quantity;
        this.specialInstructions = specialInstructions;
    }

    public long getOrderItemID() { return orderItemID; }
    public MenuItem getMenuItem() { return menuItem; }
    public int getSeatNumber() { return seatNumber; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getSpecialInstructions() { return specialInstructions; }
    public void setSpecialInstructions(String specialInstructions) { this.specialInstructions = specialInstructions; }

    public double getSubtotal() { return menuItem.getPrice() * quantity; }
}