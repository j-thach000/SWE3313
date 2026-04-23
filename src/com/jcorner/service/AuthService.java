package com.jcorner.service;

import com.jcorner.data.DataStore;
import com.jcorner.model.Employee;

public class AuthService {

    public enum Result { OK, BAD_ID, BAD_PASSWORD, LOCKED }

    public static final int MAX_ATTEMPTS = 3;

    // Attempts to log in. On success, updates Session. On failure,
    // increments the failed-login counter and locks after 3 failures
    // as specified in the design doc.

    public static Result login(String employeeID, String password) {
        Employee e = DataStore.get().employees().get(employeeID);
        if (e == null) return Result.BAD_ID;
        if (e.isLocked()) return Result.LOCKED;

        if (e.getPassword().equals(password)) {
            e.setFailedLoginCount(0);
            Session.get().setCurrentUser(e);
            return Result.OK;
        } else {
            e.setFailedLoginCount(e.getFailedLoginCount() + 1);
            if (e.getFailedLoginCount() >= MAX_ATTEMPTS) {
                e.setLocked(true);
                return Result.LOCKED;
            }
            return Result.BAD_PASSWORD;
        }
    }

    public static int remainingAttempts(String employeeID) {
        Employee e = DataStore.get().employees().get(employeeID);
        if (e == null) return MAX_ATTEMPTS;
        return Math.max(0, MAX_ATTEMPTS - e.getFailedLoginCount());
    }

    public static void logout() {
        Session.get().clear();
    }


    // Rejects trivial passwords per the design doc: 1111, 123456, etc.
    // Used by the "new employee" screen.
    
    public static boolean isPasswordAcceptable(String pw) {
        if (pw == null || pw.length() < 4) return false;
        if (pw.chars().distinct().count() <= 1) return false;          // "1111", "aaaa"
        String trivial = "0123456789";
        if (trivial.contains(pw) || new StringBuilder(trivial).reverse().toString().contains(pw)) return false;
        return true;
    }
}
