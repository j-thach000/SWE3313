package com.jcorner.service;

import com.jcorner.data.DataStore;
import com.jcorner.model.Employee;
import com.jcorner.model.Role;

import java.util.Random;

public class EmployeeService {

    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random RNG = new Random();

    // generates a unique 6-character alphanumeric employee ID. 
    public static String generateEmployeeID(Role role) {
        String prefix = switch (role) {
            case WAITER  -> "WT";
            case COOK    -> "CK";
            case BUSBOY  -> "BB";
            case MANAGER -> "MG";
        };
        for (int attempts = 0; attempts < 100; attempts++) {
            StringBuilder sb = new StringBuilder(prefix);
            for (int i = 0; i < 4; i++) sb.append(CHARS.charAt(RNG.nextInt(CHARS.length())));
            String id = sb.toString();
            if (!DataStore.get().employees().containsKey(id)) return id;
        }
        throw new IllegalStateException("Could not generate a unique employee ID");
    }

    public static Employee create(String firstName, String lastName, String email,
                                  String address, Role role, String password) {
        String id = generateEmployeeID(role);
        Employee e = new Employee(id, password, firstName, lastName, role);
        e.setEmail(email);
        e.setAddress(address);
        DataStore.get().employees().put(id, e);
        return e;
    }

    public static void delete(String employeeID) {
        DataStore.get().employees().remove(employeeID);
    }

    public static void assignTable(String employeeID, String tableID) {
        Employee e = DataStore.get().employees().get(employeeID);
        if (e != null && e.getRole() == Role.WAITER) e.getAssignedTableIDs().add(tableID);
    }

    public static void unassignTable(String employeeID, String tableID) {
        Employee e = DataStore.get().employees().get(employeeID);
        if (e != null) e.getAssignedTableIDs().remove(tableID);
    }

    public static void unlock(String employeeID) {
        Employee e = DataStore.get().employees().get(employeeID);
        if (e != null) {
            e.setLocked(false);
            e.setFailedLoginCount(0);
        }
    }
}
