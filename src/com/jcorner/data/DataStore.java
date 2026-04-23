package com.jcorner.data;

import com.jcorner.model.*;

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

}