package controller;

import Customer.Customer;
import Staff.Staff;

public class Session {

    private static Customer currentCustomer;
    private static Staff currentStaff;

    public static void setCurrentCustomer(Customer customer) {
        currentCustomer = customer;
        currentStaff = null;
    }

    public static Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public static void setCurrentStaff(Staff staff) {
        currentStaff = staff;
        currentCustomer = null;
    }

    public static Staff getCurrentStaff() {
        return currentStaff;
    }

    public static void logout() {
        currentCustomer = null;
        currentStaff = null;
    }
}