package controller;

import Customer.Customer;

public class Session {

    private static Customer currentCustomer;

    public static void setCurrentCustomer(Customer customer) {
        currentCustomer = customer;
    }

    public static Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public static void logout() {
        currentCustomer = null;
    }
}