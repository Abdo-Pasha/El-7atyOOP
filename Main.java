import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initializeTables();

        RestaurantSystem.registerCustomer("George", "Password123", LocalDate.of(2005, 5, 20), "01012345678", DietaryPreferences.NONE);

        System.out.println("--- Welcome to the Restaurant System ---");

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                Customer loggedInUser = Login.authenticate(scanner);
                if (loggedInUser != null) {
                    showCustomerMenu(scanner, loggedInUser);
                }
            } else if (choice == 2) {
                System.out.println("\n--- Registration ---");
                System.out.print("Enter Username: ");
                String user = scanner.nextLine();

                System.out.print("Enter Password (at least 8 chars, letters & numbers): ");
                String pass = scanner.nextLine();

                System.out.print("Enter Phone (e.g., 01012345678): ");
                String phone = scanner.nextLine();

                System.out.println("Select Dietary Preference:");
                int index = 1;
                for (DietaryPreferences d : DietaryPreferences.values()) {
                    System.out.println(index++ + ". " + d);
                }
                System.out.print("Choice: ");
                int dietChoice = scanner.nextInt();
                scanner.nextLine();

                DietaryPreferences diet = DietaryPreferences.NONE;
                if(dietChoice >= 1 && dietChoice <= DietaryPreferences.values().length) {
                    diet = DietaryPreferences.values()[dietChoice - 1];
                }

                RestaurantSystem.registerCustomer(user, pass, LocalDate.of(2000, 1, 1), phone, diet);
            } else if (choice == 3) {
                System.out.println("Exiting System. Goodbye!");
                break;
            } else {
                System.out.println("Invalid option, try again.");
            }
        }
        scanner.close();
    }

    private static void initializeTables() {
        for (int i = 0; i < 15; i++) {
            TableType type = (i % 3 == 0) ? TableType.VIP : TableType.INDOOR;
            int capacity = (i % 2 == 0) ? 4 : 2;
            RestaurantSystem.tables[i] = new Table(i + 1, capacity, type);
        }
        System.out.println("System initialized with 15 tables.");
    }

    private static void showCustomerMenu(Scanner scanner, Customer customer) {
        while (true) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. View Profile");
            System.out.println("2. Book a Table");
            System.out.println("3. View My Reservations");
            System.out.println("4. Cancel Reservation");
            System.out.println("5. View All Tables Status (New!)");
            System.out.println("6. Logout");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                customer.viewProfile();
            } else if (choice == 2) {
                System.out.print("Enter Party Size: ");
                int partySize = scanner.nextInt();
                scanner.nextLine();

                LocalDateTime bookingTime = LocalDateTime.now().plusDays(1).withHour(19).withMinute(0);
                List<Table> available = RestaurantSystem.viewAvailableTables(bookingTime, partySize);

                if (!available.isEmpty()) {
                    System.out.println("\nAvailable Tables:");
                    for (Table t : available) {
                        System.out.println(t);
                    }

                    System.out.println("\n* Note: We see your dietary preference is [" + customer.getDietaryPreferences() + "]. The chef will be notified!");

                    System.out.print("Enter Table Number to book: ");
                    int tableNum = scanner.nextInt();
                    scanner.nextLine();

                    if (tableNum > 0 && tableNum <= 15) {
                        RestaurantSystem.makeReservation(customer, RestaurantSystem.tables[tableNum - 1], bookingTime, partySize);
                    } else {
                        System.out.println("Invalid Table Number.");
                    }
                } else {
                    System.out.println("No tables available for this party size.");
                }
            } else if (choice == 3) {
                RestaurantSystem.viewCustomerReservations(customer);
            } else if (choice == 4) {
                System.out.print("Enter Reservation ID to cancel: ");
                int resId = scanner.nextInt();
                scanner.nextLine();
                RestaurantSystem.cancelReservation(customer, resId);
            } else if (choice == 5) {
                System.out.println("\n--- All Tables Status ---");
                for (Table t : RestaurantSystem.tables) {
                    if (t != null) {
                        System.out.println(t);
                    }
                }
            } else if (choice == 6) {
                System.out.println("Logging out...");
                break;
            }
        }
    }
}