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
}