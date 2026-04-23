package com.jcorner.model;

// Represents one of the 36 tables in the 6x6 grid (A1..F6).
public class RestaurantTable {
    private final String tableID;  // "A1", "F6" etc.
    private TableStatus status;
    private int capacity;          // 1..4
    private final String col;      // "A".."F"
    private final int row;         // 1..6
    private int currentGuestCount;

    public RestaurantTable(String col, int row, int capacity) {
        this.col = col;
        this.row = row;
        this.capacity = capacity;
        this.tableID = col + row;
        this.status = TableStatus.READY;
        this.currentGuestCount = 0;
    }

    public String getTableID() { return tableID; }
    public TableStatus getStatus() { return status; }
    
    public void setStatus(TableStatus status) { this.status = status; }
    
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    
    public String getCol() { return col; }
    public int getRow() { return row; }
    
    public int getCurrentGuestCount() { return currentGuestCount; }
    public void setCurrentGuestCount(int currentGuestCount) { this.currentGuestCount = currentGuestCount; }
}