import java.time.LocalDate;
import java.util.List;

public class Admin extends Staff {

    public Admin(String username, String password, LocalDate dateOfBirth, WorkingHours workingHours) {
        super(username, password, dateOfBirth, Role.ADMIN, workingHours);
    }

    // View Functionalities
    public List<Customer> viewAllCustomers() {
        return RestaurantDatabase.customers;
    }

    public List<Table> viewAllTables() {
        return RestaurantDatabase.tables;
    }

    public List<Reservation> viewAllReservations() {
        return RestaurantDatabase.reservations;
    }

    // Table CRUD
    public void addTable(Table table) { RestaurantDatabase.tables.add(table); }
    public void removeTable(Table table) { RestaurantDatabase.tables.remove(table); }

    // Category CRUD
    public void addCategory(MenuCategory category) { RestaurantDatabase.categories.add(category); }
    public void removeCategory(MenuCategory category) { RestaurantDatabase.categories.remove(category); }

    // Menu Item CRUD
    public void addMenuItem(MenuItem item) { RestaurantDatabase.menuItems.add(item); }
    public void removeMenuItem(MenuItem item) { RestaurantDatabase.menuItems.remove(item); }
}