package com.jcorner.model;

import java.util.ArrayList;
import java.util.List;

public class MenuCategory {
    private final String categoryID;
    private String name;
    private final int sortOrder;
    private final List<MenuItem> items = new ArrayList<>();

    public MenuCategory(String categoryID, String name, int sortOrder) {
        this.categoryID = categoryID;
        this.name = name;
        this.sortOrder = sortOrder;
    }

    public String getCategoryID() { return categoryID; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getSortOrder() { return sortOrder; }
    public List<MenuItem> getItems() { return items; }

    // Filters out items flagged isAvailable=false so they don't appear in ordering UI.
    // using built-in library functions for succinctness
    public List<MenuItem> getAvailableItems() {
        return items.stream().filter(MenuItem::isAvailable).toList();
    }
}