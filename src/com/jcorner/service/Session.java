package com.jcorner.service;

import com.jcorner.model.Employee;

// session state for desktop app, one user logged in at a time

public class Session {
    private static final Session INSTANCE = new Session();
    public static Session get() { return INSTANCE; }

    private Employee currentUser;
    private String selectedTableID;
    private String selectedOrderID;
    private Integer selectedSeat;       // 1..4

    private Session() {}

    public Employee getCurrentUser() { return currentUser; }
    public void setCurrentUser(Employee currentUser) { this.currentUser = currentUser; }

    public String getSelectedTableID() { return selectedTableID; }
    public void setSelectedTableID(String selectedTableID) { this.selectedTableID = selectedTableID; }

    public String getSelectedOrderID() { return selectedOrderID; }
    public void setSelectedOrderID(String selectedOrderID) { this.selectedOrderID = selectedOrderID; }

    public Integer getSelectedSeat() { return selectedSeat; }
    public void setSelectedSeat(Integer selectedSeat) { this.selectedSeat = selectedSeat; }

    public void clear() {
        currentUser = null;
        selectedTableID = null;
        selectedOrderID = null;
        selectedSeat = null;
    }
}