package com.jcorner.data;

import com.jcorner.model.*;
import com.jcorner.model.RestaurantTable;

import java.util.*;


// in-memory data store. 
// Implemented using a singleton pattern (ONLY one instance of DataStore, globally accessible)

public class DataStore {

    private static final DataStore INSTANCE = new DataStore();
    public static DataStore get() { return INSTANCE; }

    private final Map<String, Employee> employees = new LinkedHashMap<>();
    private final Map<String, RestaurantTable> tables = new LinkedHashMap<>();
    private final Map<String, MenuCategory> categories = new LinkedHashMap<>();
    private final Map<String, MenuItem> menuItems = new LinkedHashMap<>();
    private final Map<String, FoodOrder> orders = new LinkedHashMap<>();
    private final Map<String, InventoryItem> inventory = new LinkedHashMap<>();
    private final List<ShiftRecord> shiftRecords = new ArrayList<>();
    private final List<RefundRequest> refundRequests = new ArrayList<>();

    private int orderCounter = 4800;       // matches design doc's ORD-048xx style

    private DataStore() {}

    public Map<String, Employee> employees() { return employees; }
    public Map<String, RestaurantTable> tables() { return tables; }
    public Map<String, MenuCategory> categories() { return categories; }
    public Map<String, MenuItem> menuItems() { return menuItems; }
    public Map<String, FoodOrder> orders() { return orders; }
    public Map<String, InventoryItem> inventory() { return inventory; }
    public List<ShiftRecord> shiftRecords() { return shiftRecords; }
    public List<RefundRequest> refundRequests() { return refundRequests; }

    // Menu needs to return in a specific order
    public List<MenuCategory> categoriesSorted() {
        return categories.values().stream()
                .sorted(Comparator.comparingInt(MenuCategory::getSortOrder))
                .toList();
    }

    // Generates the next unique order ID in "ORD-NNNNN" format. 
    public synchronized String nextOrderId() {
        orderCounter++;
        return String.format("ORD-%05d", orderCounter);
    }

    // Active (not clocked out) shift record for an employee, or null. 
    public ShiftRecord activeShiftFor(String employeeID) {
        for (ShiftRecord r : shiftRecords) {
            if (r.getEmployeeID().equals(employeeID) && r.isActive()) return r;
        }
        return null;
    }

    public FoodOrder activeOrderForTable(String tableID) {
        for (FoodOrder o : orders.values()) {
            if (o.getTableID().equals(tableID)
                    && o.getStatus() != OrderStatus.SERVED
                    && o.getStatus() != OrderStatus.CANCELLED) {
                return o;
            }
        }
        return null;
    }
}