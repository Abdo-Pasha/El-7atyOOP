import java.time.LocalDate;
import java.util.ArrayList;

public class RestaurantDatabase {
    // Shared global in-memory static lists accessible by all team members
    public static ArrayList<Customer> customers = new ArrayList<>();
    public static ArrayList<Table> tables = new ArrayList<>();
    public static ArrayList<Reservation> reservations = new ArrayList<>();
    public static ArrayList<Order> orders = new ArrayList<>();
    public static ArrayList<Invoice> invoices = new ArrayList<>();
    public static ArrayList<MenuItem> menuItems = new ArrayList<>();
    public static ArrayList<MenuCategory> categories = new ArrayList<>();
    public static ArrayList<Staff> staff = new ArrayList<>();

    // Static initializer block runs automatically when class is loaded
    static {
        initializeDummyData();
    }

    public static void initializeDummyData() {
        // Staff dummy data
        WorkingHours hours = new WorkingHours("09:00 AM", "05:00 PM");
        Admin admin = new Admin("admin1", "pass123", LocalDate.of(1990, 1, 1), hours);
        Waiter waiter = new Waiter("waiter1", "pass123", LocalDate.of(1998, 5, 12), hours);
        staff.add(admin);
        staff.add(waiter);
    }

    // Basic Search / Retrieval Helper Methods
    public static Customer findCustomerByUsername(String username) {
        for (Customer c : customers) {
            if (c.getUsername().equalsIgnoreCase(username)) return c;
        }
        return null;
    }

    public static Staff findStaffByUsername(String username) {
        for (Staff s : staff) {
            if (s.getUsername().equalsIgnoreCase(username)) return s;
        }
        return null;
    }

    // NOW THIS WILL USE YOUR CUSTOM CLASS
    public static MenuItem findMenuItemByName(String name) {
        for (MenuItem item : menuItems) {
            if (item.getName().equalsIgnoreCase(name)) return item;
        }
        return null;
    }
}