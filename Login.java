import java.util.Scanner;

public class Login {
    public static Customer authenticate(Scanner scanner) {
        System.out.println("\n--- Login ---");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        Customer loggedInCustomer = RestaurantSystem.loginCustomer(username, password);

        if (loggedInCustomer != null) {
            System.out.println("Login successful! Welcome " + loggedInCustomer.getUsername());
        } else {
            System.out.println("Login failed: Invalid credentials.");
        }

        return loggedInCustomer;
    }
}