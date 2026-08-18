package Staff;
import Customer.Customer;
import Customer.DietaryPreferences;
import Order.Invoice;
import Order.MenuCategory;
import Order.MenuItem;
import Order.Order;
import Reservation.Reservation;
import Table.Table;
import Table.TableType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RestaurantDatabase {
    // Shared global in-memory static lists accessible by all team members
    public static ArrayList<Customer> customers = new ArrayList<>();
    public static ArrayList<Waiter> waiters = new ArrayList<>();
    public static ArrayList<Table> tables = new ArrayList<>();
    public static ArrayList<Reservation> reservations = new ArrayList<>();
    public static ArrayList<Order> orders = new ArrayList<>();
    public static ArrayList<Invoice> invoices = new ArrayList<>();
    public static ArrayList<MenuItem> menuItems = new ArrayList<>();
    public static ArrayList<MenuCategory> categories = new ArrayList<>();
    public static ArrayList<Staff> staff = new ArrayList<>();
    public static final Map<String, String> MENU_ITEM_IMAGES = new HashMap<>();

    // Static initializer block runs automatically when class is loaded
    static {
        initializeDummyData();
    }

    public static void initializeDummyData() {
        customers.clear();
        tables.clear();
        reservations.clear();
        orders.clear();
        invoices.clear();
        menuItems.clear();
        categories.clear();
        staff.clear();
        MENU_ITEM_IMAGES.clear();

        WorkingHours mainHours = new WorkingHours("09:00 AM", "05:00 PM");

        Admin admin = new Admin("AserAdmin", "admin123", LocalDate.of(2004, 6, 10), mainHours);
        Waiter ahmed = new Waiter("Ahmed", "waiter123", LocalDate.of(1997, 3, 20), mainHours);
        staff.add(admin);
        staff.add(ahmed);

        Customer abdo = new Customer("Abdo", "customer123", LocalDate.of(1990, 5, 15), "01001234567", DietaryPreferences.NONE);
        customers.add(abdo);

        Waiter waiter = new Waiter("Ahmed", "waiter123", LocalDate.of(1995, 3, 20), mainHours);
        waiters.add(waiter);

        tables.add(new Table(1, 2, TableType.INDOOR));
        tables.add(new Table(2, 4, TableType.INDOOR));
        tables.add(new Table(3, 4, TableType.INDOOR));
        tables.add(new Table(4, 6, TableType.OUTDOOR));
        tables.add(new Table(5, 6, TableType.OUTDOOR));
        tables.add(new Table(6, 8, TableType.VIP));
        tables.add(new Table(7, 4, TableType.VIP));
        tables.add(new Table(8, 10, TableType.PRIVATE_ROOM));
        tables.add(new Table(9, 10, TableType.PRIVATE_ROOM));

        MenuCategory mainCourse = new MenuCategory(1, "Main Course", "Main food dishes");
        MenuCategory beverages = new MenuCategory(2, "Beverages", "Cold and hot drinks");
        MenuCategory desserts = new MenuCategory(3, "Desserts", "Sweet finishing items");
        categories.add(mainCourse);
        categories.add(beverages);
        categories.add(desserts);

        MenuItem mixedGrill = new MenuItem(1, "Mixed Grill", 450.0, "Charcoal grilled meats", mainCourse, true);
        MenuItem shishTawook = new MenuItem(2, "Shish Tawook", 250.0, "Grilled chicken skewers", mainCourse, true);
        MenuItem soda = new MenuItem(3, "Soda", 50.0, "Canned soda", beverages, true);
        MenuItem freshJuice = new MenuItem(4, "Fresh Juice", 80.0, "Fresh orange juice", beverages, true);
        MenuItem chickenMeal = new MenuItem(5, "Chicken Meal", 250.0, "Grilled chicken served with fries", mainCourse, true);
        MenuItem beefBurger = new MenuItem(6, "Beef Burger", 200.0, "Beef burger served with fries", mainCourse, true);
        MenuItem cola = new MenuItem(7, "Cola", 45.0, "Cold soft drink", beverages, true);
        MenuItem specialMeal = new MenuItem(8, "Special Meal", 300.0, "Special restaurant meal", mainCourse, false);
        MenuItem cake = new MenuItem(9, "Cake", 120.0, "Chocolate cake slice", desserts, true);

        menuItems.add(mixedGrill);
        menuItems.add(shishTawook);
        menuItems.add(soda);
        menuItems.add(freshJuice);
        menuItems.add(chickenMeal);
        menuItems.add(beefBurger);
        menuItems.add(cola);
        menuItems.add(specialMeal);
        menuItems.add(cake);

        MENU_ITEM_IMAGES.put("Mixed Grill", "mixed-grill.jpg");
        MENU_ITEM_IMAGES.put("Shish Tawook", "shish-tawook.jpg");
        MENU_ITEM_IMAGES.put("Soda", "soda.jpg");
        MENU_ITEM_IMAGES.put("Fresh Juice", "fresh-juice.jpg");
        MENU_ITEM_IMAGES.put("Chicken Meal", "chicken-meal.jpg");
        MENU_ITEM_IMAGES.put("Beef Burger", "beef-burger.jpg");
        MENU_ITEM_IMAGES.put("Cola", "cola.jpg");
        MENU_ITEM_IMAGES.put("Special Meal", "special-meal.jpg");
        MENU_ITEM_IMAGES.put("Cake", "cake.jpg");
    }

    public static void addCustomer(Customer customer) {
        if (customer == null) return;
        if (findCustomerByUsername(customer.getUsername()) != null) return;
        customers.add(customer);
    }

    public static void addStaff(Staff newStaff) {
        if (newStaff == null) return;
        if (findStaffByUsername(newStaff.getUsername()) != null) return;
        staff.add(newStaff);
    }

    public static Staff createStaffAccount(String username, String password, LocalDate dateOfBirth, Role role, WorkingHours workingHours) {
        if (username == null || username.trim().isEmpty()) return null;
        if (password == null || password.trim().isEmpty()) return null;
        if (findStaffByUsername(username) != null) return null;

        Staff newStaff;
        if (role == Role.ADMIN) {
            newStaff = new Admin(username, password, dateOfBirth, workingHours);
        } else if (role == Role.WAITER) {
            newStaff = new Waiter(username, password, dateOfBirth, workingHours);
        } else {
            return null;
        }

        staff.add(newStaff);
        return newStaff;
    }

    public static Staff authenticateStaff(String username, String password) {
        for (Staff s : staff) {
            if (s.getUsername().equalsIgnoreCase(username) && s.getPassword().equals(password)) {
                return s;
            }
        }
        return null;
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

    public static MenuItem findMenuItemByName(String name) {
        for (MenuItem item : menuItems) {
            if (item.getName().equalsIgnoreCase(name)) return item;
        }
        return null;
    }

    public static String getImageNameForMenuItem(String menuItemName) {
        if (menuItemName == null) return "default.jpg";
        return MENU_ITEM_IMAGES.getOrDefault(menuItemName, "default.jpg");
    }
}