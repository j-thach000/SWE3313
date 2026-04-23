package com.jcorner.model;

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

    // manager view fields
    private String email;
    private String address;
    private String photoPath;   // optional atm

    private int failedLoginCount = 0;
    private boolean locked = false;

    // tables scheduled to waiters
    private final Set<String> assignedTableIds = new HashSet<>();

    // days of the week employee is scheduled (0 = Sunday, 1 = Monday, etc.) 
    private final boolean[] schedule = new boolean[7];

    public Employee(String employeeID, String password, String firstName, String lastName, Role role) {
        this.employeeID = employeeID;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public String getEmployeeID() { return employeeID; }
    public void setEmployeeID(String employeeID) {this.employeeID = employeeID; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFullName() { return firstName + " " + lastName; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    public int getFailedLoginCount() { return failedLoginCount; }
    public void setFailedLoginCount(int failedLoginCount) { this.failedLoginCount = failedLoginCount; }

    public boolean isLocked() { return locked; }
    public void setLocked(boolean locked) { this.locked = locked; }

    public Set<String> getAssignedTableIds() { return assignedTableIds; }

    public boolean worksDay(int dayOfWeek /* 0=Sun..6=Sat */) {
    return schedule[dayOfWeek];
}
    public void setWorksDay(int dayOfWeek, boolean works) {
        schedule[dayOfWeek] = works;
    }
}
