package com.jcorner.data;

import com.jcorner.model.*;
import com.offlimes.jcorner.model.RestTable;

import java.util.*;


// In-memory data store. 
// Implemented using a singleton pattern (ONLY one instance of DataStore, globally accessible)

public class DataStore {

    private static final DataStore INSTANCE = new DataStore();
    public static DataStore get() { return INSTANCE; }

    private final Map<String, Employee> employees = new LinkedHashMap<>();
    private final Map<String, RestTable> tables = new LinkedHashMap<>();
    private final Map<String, MenuCategory> categories = new LinkedHashMap<>();
    private final Map<String, MenuItem> menuItems = new LinkedHashMap<>();
    private final Map<String, FoodOrder> orders = new LinkedHashMap<>();
    private final Map<String, InventoryItem> inventory = new LinkedHashMap<>();
    private final List<ShiftRecord> shiftRecords = new ArrayList<>();
    private final List<RefundRequest> refundRequests = new ArrayList<>();

    private int orderCounter = 4800;       // matches design doc's ORD-048xx style

    private DataStore() {}

    public Map<String, Employee> employees() { return employees; }
    public Map<String, RestTable> tables() { return tables; }
    public Map<String, MenuCategory> categories() { return categories; }
    public Map<String, MenuItem> menuItems() { return menuItems; }
    public Map<String, FoodOrder> orders() { return orders; }
    public Map<String, InventoryItem> inventory() { return inventory; }
    public List<ShiftRecord> shiftRecords() { return shiftRecords; }
    public List<RefundRequest> refundRequests() { return refundRequests; }
}