import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Waiter extends Staff {
    private List<Table> assignedTables;

    public Waiter(String username, String password, LocalDate dateOfBirth, WorkingHours workingHours) {
        super(username, password, dateOfBirth, Role.WAITER, workingHours);
        this.assignedTables = new ArrayList<>();
    }

    public List<Table> getAssignedTables() {
        return assignedTables;
    }

    public void assignTable(Table table) {
        if (table != null && !assignedTables.contains(table)) {
            assignedTables.add(table);
        }
    }

    public void takeOrder(Order order) {
        if (order != null) {
            RestaurantDatabase.orders.add(order);
        }
    }

    // Updated: Changes the status of an existing order
    public void updateOrder(Order order, String newStatus) {
        if (order != null && newStatus != null) {
            order.setStatus(newStatus);
        }
    }

    public void manageSeating(Table table, String status) {
        if (table != null && status != null) {
            table.setStatus(status);
        }
    }

    public void checkoutAssignedTable(Table table, Invoice invoice) {
        if (table != null && invoice != null) {
            RestaurantDatabase.invoices.add(invoice);
            table.setStatus("AVAILABLE");
            assignedTables.remove(table);
        }
    }
}