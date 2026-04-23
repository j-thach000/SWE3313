package com.jcorner.model;

public class MenuItem {
    private final String itemID;
    private String name;
    private double price;
    private String description;
    private boolean isAvailable = true;
    private MenuCategory category;

    public MenuItem(String itemID, String name, double price, String description, MenuCategory category) {
        this.itemID = itemID;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        category.getItems().add(this);
    }

    public String getItemID() { return itemID; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public MenuCategory getCategory() { return category; }

    @Override
    public String toString() { return name; } 
}