package com.jcorner.service;

import com.jcorner.data.DataStore;
import com.jcorner.model.RestaurantTable;
import com.jcorner.model.TableStatus;

public class TableService {

    // Changes table status (busboy clean, waiter mark dirty, etc.). 
    public static void changeStatus(String tableID, TableStatus newStatus) {
        RestTable t = DataStore.get().tables().get(tableID);
        if (t != null) t.setStatus(newStatus);
    }

    public static void markClean(String tableID) {
        changeStatus(tableID, TableStatus.READY);
    }

    public static void markDirty(String tableID) {
        changeStatus(tableID, TableStatus.DIRTY);
    }

    public static void markOccupied(String tableID, int guestCount) {
        RestTable t = DataStore.get().tables().get(tableID);
        if (t != null) {
            t.setStatus(TableStatus.OCCUPIED);
            t.setCurrentGuestCount(guestCount);
        }
    }
}