package Reservation;

import Customer.Customer;
import Table.Table;

import java.time.LocalDateTime;

public class Reservation {
    private static int idCounter = 1;
    private int reservationId;
    private Customer customer;
    private Table table;
    private LocalDateTime reservationTime;
    private int partySize;
    private ReservationStatus status;

    public Reservation(Customer customer, Table table, LocalDateTime reservationTime, int partySize) {
        this.reservationId = idCounter++;
        this.customer = customer;
        this.table = table;
        this.reservationTime = reservationTime;
        this.partySize = partySize;
        this.status = ReservationStatus.CONFIRMED;
    }

    public int getReservationId() { return reservationId; }
    public Customer getCustomer() { return customer; }
    public Table getTable() { return table; }
    public LocalDateTime getReservationTime() { return reservationTime; }
    public int getPartySize() { return partySize; }
    public ReservationStatus getStatus() { return status; }

    public void setStatus(ReservationStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId + " | Customer: " + customer.getUsername() +
                " | Table: " + table.getTableNumber() + " | Time: " + reservationTime +
                " | Party Size: " + partySize + " | Status: " + status;
    }
}