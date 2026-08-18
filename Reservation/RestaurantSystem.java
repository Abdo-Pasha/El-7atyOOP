package Reservation;

import Customer.Customer;
import Customer.DietaryPreferences;
import Staff.RestaurantDatabase;
import Table.Table;
import Table.TableStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantSystem {
    public static ArrayList<Customer> customers = RestaurantDatabase.customers;
    public static Table[] tables = new Table[15];
    public static ArrayList<Reservation> reservations = new ArrayList<>();

    public static void syncTables() {
        tables = RestaurantDatabase.tables.toArray(new Table[0]);
    }
    
    public static final LocalTime OPENING_TIME = LocalTime.of(12, 0);
    public static final LocalTime CLOSING_TIME = LocalTime.of(23, 0);
    private static final int RESERVATION_DURATION_HOURS = 2;

    public static Customer registerCustomer(String username, String password, LocalDate dob, String phone, DietaryPreferences diet) {
        for (Customer c : RestaurantDatabase.customers) {
            if (c.getUsername().equals(username)) {
                System.out.println("Registration failed: Username already exists.");
                return null;
            }
        }
        try {
            Customer newCustomer = new Customer(username, password, dob, phone, diet);
            RestaurantDatabase.addCustomer(newCustomer);
            System.out.println("Customer registered successfully!");
            return newCustomer;
        } catch (IllegalArgumentException e) {
            System.out.println("Registration failed: " + e.getMessage());
            return null;
        }
    }

    public static Customer loginCustomer(String username, String password) {
        for (Customer c : RestaurantDatabase.customers) {
            if (c.getUsername().equals(username) && c.getPassword().equals(password)) {
                return c;
            }
        }
        return null;
    }

    // public static void initializeSystem() {
    //     customers = RestaurantDatabase.customers;
    // }
   
    // public static void initializeTables() {

    //     tables[0] = new Table(1, 2, TableType.INDOOR);
    //     tables[1] = new Table(2, 4, TableType.INDOOR);
    //     tables[2] = new Table(3, 4, TableType.INDOOR);
    //     tables[3] = new Table(4, 6, TableType.OUTDOOR);
    //     tables[4] = new Table(5, 6, TableType.OUTDOOR);
    //     tables[5] = new Table(6, 8, TableType.VIP);
    //     tables[6] = new Table(7, 4, TableType.VIP);
    //     tables[7] = new Table(8, 10, TableType.PRIVATE_ROOM);
    //     tables[8] = new Table(9, 10, TableType.PRIVATE_ROOM);
    // }

    public static List<Table> viewAvailableTables(LocalDateTime requestTime, int partySize) {
        syncTables();
        List<Table> available = new ArrayList<>();

        LocalTime reqTime = requestTime.toLocalTime();
        if (reqTime.isBefore(OPENING_TIME) || reqTime.isAfter(CLOSING_TIME.minusHours(RESERVATION_DURATION_HOURS))) {
            System.out.println("Cannot book: Restaurant is closed or too close to closing time.");
            return available;
        }

        for (Table table : tables) {
            if (table != null && table.getCapacity() >= partySize && isTableAvailable(table, requestTime)) {
                available.add(table);
            }
        }
        return available;
    }

    private static boolean isTableAvailable(Table table, LocalDateTime requestTime) {
        for (Reservation res : reservations) {
            if (res.getTable().getTableNumber() == table.getTableNumber() && res.getStatus() == ReservationStatus.CONFIRMED) {
                LocalDateTime resEnd = res.getReservationTime().plusHours(RESERVATION_DURATION_HOURS);
                LocalDateTime reqEnd = requestTime.plusHours(RESERVATION_DURATION_HOURS);

                if (requestTime.isBefore(resEnd) && reqEnd.isAfter(res.getReservationTime())) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Reservation makeReservation(Customer customer, Table table, LocalDateTime time, int partySize) {
        if (table == null) {
            System.out.println("Reservation failed: Invalid table.");
            return null;
        }

        if (partySize > table.getCapacity()) {
            System.out.println("Reservation failed: Party size exceeds table capacity.");
            return null;
        }

        List<Table> availableNow = viewAvailableTables(time, partySize);
        if (!availableNow.contains(table)) {
            System.out.println("Reservation failed: Table is not available at this time.");
            return null;
        }

        Reservation newRes = new Reservation(customer, table, time, partySize);
        reservations.add(newRes);

        // التعديل هنا: تغيير حالة الطاولة إلى محجوزة
        table.setStatus(TableStatus.RESERVED);

        customer.addLoyaltyPoints(10);
        System.out.println("Reservation successful! ID: " + newRes.getReservationId());
        return newRes;
    }

    public static void viewCustomerReservations(Customer customer) {
        System.out.println("=== Reservations for " + customer.getUsername() + " ===");
        boolean found = false;
        for (Reservation res : reservations) {
            if (res.getCustomer().getUsername().equals(customer.getUsername())) {
                System.out.println(res);
                found = true;
            }
        }
        if (!found) System.out.println("No reservations found.");
    }

    public static void cancelReservation(Customer customer, int reservationId) {
        for (Reservation res : reservations) {
            if (res.getReservationId() == reservationId && res.getCustomer().getUsername().equals(customer.getUsername())) {
                if (res.getStatus() == ReservationStatus.CONFIRMED) {
                    res.setStatus(ReservationStatus.CANCELLED);
                    System.out.println("Reservation ID " + reservationId + " has been cancelled.");

                    boolean isStillReserved = false;
                    for (Reservation otherRes : reservations) {
                        if (otherRes.getTable().getTableNumber() == res.getTable().getTableNumber() && otherRes.getStatus() == ReservationStatus.CONFIRMED) {
                            isStillReserved = true;
                            break;
                        }
                    }
                    if (!isStillReserved) {
                        res.getTable().setStatus(TableStatus.AVAILABLE);
                    }
                    return;
                } else {
                    System.out.println("Reservation is already cancelled or completed.");
                    return;
                }
            }
        }
        System.out.println("Reservation not found or permission denied.");
    }

    public static Table[] getTables() {
        return tables;
    }

}
