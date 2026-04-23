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

}