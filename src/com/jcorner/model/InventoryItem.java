package com.jcorner.model;

import java.time.LocalDateTime;

public class InventoryItem {
    private final String itemID;
    private String name;
    private int quantity;
    private String unit;              // "lbs", "ct", "heads", "boxes"
    private LocalDateTime lastChecked;
    private int lowThreshold;         // below this => LOW

    public InventoryItem(String itemID, String name, int quantity, String unit, int lowThreshold) {
        this.itemID = itemID;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.lowThreshold = lowThreshold;
        this.lastChecked = LocalDateTime.now();
    }

    public String getItemID() { return itemID; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getQuantity() { return quantity; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public LocalDateTime getLastChecked() { return lastChecked; }
    public int getLowThreshold() { return lowThreshold; }
    public void setLowThreshold(int lowThreshold) { this.lowThreshold = lowThreshold; }

    // Updates quantity and last-checked timestamp.
    public void update(int newQuantity) {
        this.quantity = newQuantity;
        this.lastChecked = LocalDateTime.now();
    }

    public StockStatus getStockStatus() {
        if (quantity <= 0) return StockStatus.OUT_OF_STOCK;
        if (quantity <= lowThreshold) return StockStatus.LOW;
        return StockStatus.IN_STOCK;
    }
}