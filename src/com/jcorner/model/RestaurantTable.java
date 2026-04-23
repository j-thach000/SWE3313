package com.jcorner.model;

public class RestaurantTable {
    private final String tableId;  // "A1", "F6" etc.
    private TableStatus status;
    private int capacity;          // 1..4
    private final String col;      // "A".."F"
    private final int row;         // 1..6
    private int currentGuestCount;

    public RestaurantTable(String col, int row, int capacity) {
        this.col = col;
        this.row = row;
        this.capacity = capacity;
        this.tableId = col + row;
        this.status = TableStatus.READY;
        this.currentGuestCount = 0;
    }
}