package com.offlimes.jcorner.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a staff member. The Role field determines which dashboard
 * is displayed after login. Design-doc fields: employeeId, password,
 * firstName, lastName, role. Extra fields support the manager's
 * "manage employees" screen (email, address, photo, schedule, table
 * assignments for waiters).
 */

public class Employee {
    private String employeeID;  // 6-character alphanumeric 
    private String password;    // plain-text in demo otherwise would hash
    private String firstName;
    private String lastName;
    private Role role;

    private final Set<String> assignedTableIds = new HashSet<>();

    public Employee(String employeeID, String password, String firstName, String lastName, Role role) {
        this.employeeID = employeeID;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
}
