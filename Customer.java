package Customer;

import java.time.LocalDate;

public class Customer {
    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private double balance;
    private String phoneNumber;
    private int loyaltyPoints;
    private DietaryPreferences dietaryPreferences;

    public Customer(String username, String password, LocalDate dateOfBirth, String phoneNumber, DietaryPreferences dietaryPreferences) {
        if (!validatePassword(password)) {
            throw new IllegalArgumentException("Weak password! Must be at least 8 characters long and contain both letters and numbers.");
        }
        if (!validatePhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number! Must be an 11-digit Egyptian number (e.g., 010...).");
        }

        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.dietaryPreferences = dietaryPreferences;
        this.balance = 0.0;
        this.loyaltyPoints = 0;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public static boolean validatePassword(String pwd) {
        return pwd != null && pwd.length() >= 8 && pwd.matches(".*[a-zA-Z].*") && pwd.matches(".*\\d.*");
    }

    public static boolean validatePhoneNumber(String phone) {
        return phone != null && phone.matches("^01[0125][0-9]{8}$");
    }

    public void viewProfile() {
        System.out.println("=== Customer Profile ===");
        System.out.println("Username: " + username);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Balance: $" + balance);
        System.out.println("Loyalty Points: " + loyaltyPoints);
        System.out.println("Dietary Preferences: " + dietaryPreferences);
        System.out.println("========================");
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public double getBalance() { return balance; }
    public int getLoyaltyPoints() { return loyaltyPoints; }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void addBalance(double amount) { this.balance += amount; }
    public void addLoyaltyPoints(int points) { this.loyaltyPoints += points; }
    public DietaryPreferences getDietaryPreferences() { return dietaryPreferences; }
}