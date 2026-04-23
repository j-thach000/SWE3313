package com.jcorner.model;

import java.time.LocalDateTime;

public class InventoryItem {
    private final String itemID;
    private String name;
    private int quantity;
    private String unit;              // "lbs", "ct", "heads", "boxes"
    private LocalDateTime lastChecked;
    private int lowThreshold;         // below this => LOW
}