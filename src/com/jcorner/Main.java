package com.jcorner;

import com.jcorner.data.DataStore;
import com.jcorner.data.Seeder;
import com.jcorner.model.Employee;
import com.jcorner.service.Session;

public class Main {
    public static void main(String[] args) {
        Seeder.seed();

        // 1. Singleton check
        Session s1 = Session.get();
        Session s2 = Session.get();
        System.out.println("Same instance: " + (s1 == s2));       // true

        // 2. Initial state should be null
        System.out.println("Starts empty: " + (s1.getCurrentUser() == null));  // true

        // 3. Set and retrieve
        Employee mgr = DataStore.get().employees().get("MG1001");
        Session.get().setCurrentUser(mgr);
        Session.get().setSelectedTableID("A1");
        Session.get().setSelectedSeat(2);

        System.out.println("User stored: " + Session.get().getCurrentUser().getFullName());
        System.out.println("Table stored: " + Session.get().getSelectedTableID());
        System.out.println("Seat stored: " + Session.get().getSelectedSeat());

        // 4. Clear wipes everything
        Session.get().clear();
        System.out.println("After clear user: " + Session.get().getCurrentUser());        // null
        System.out.println("After clear table: " + Session.get().getSelectedTableID());   // null
        System.out.println("After clear seat: " + Session.get().getSelectedSeat());       // null
    }
}