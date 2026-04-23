package com.jcorner;

import com.jcorner.data.DataStore;
import com.jcorner.data.Seeder;

public class Main {
    public static void main(String[] args) {
        Seeder.seed();
        System.out.println("Tables:    " + DataStore.get().tables().size());
        System.out.println("Menu:      " + DataStore.get().menuItems().size());
        System.out.println("Employees: " + DataStore.get().employees().size());
        System.out.println("Inventory: " + DataStore.get().inventory().size());
    }
}