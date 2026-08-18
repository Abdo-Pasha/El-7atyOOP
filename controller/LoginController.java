package controller;

import Customer.Customer;
import Reservation.RestaurantSystem;
import Staff.RestaurantDatabase;
import Staff.Role;
import Staff.Staff;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private void login() {

        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter username and password.");
            return;
        }

        Customer customer = RestaurantSystem.loginCustomer(username, password);
        if (customer != null) {
            Session.setCurrentCustomer(customer);
            messageLabel.setText("Login successful!");
            openDashboard("/view/dashboard.fxml", "EL7ATY - Dashboard", 900, 600);
            return;
        }

        Staff staff = RestaurantDatabase.findStaffByUsername(username);
        if (staff != null && staff.getPassword().equals(password)) {
            Session.setCurrentStaff(staff);
            messageLabel.setText("Login successful! Welcome, " + staff.getUsername());

            if (staff.getRole() == Role.ADMIN) {
                openDashboard("/view/AdminDashboard.fxml", "EL7ATY - Admin Dashboard", 1100, 700);
            } else if (staff.getRole() == Role.WAITER) {
                openDashboard("/view/WaiterDashboard.fxml", "EL7ATY - Waiter Dashboard", 1100, 700);
            } else {
                openDashboard("/view/dashboard.fxml", "EL7ATY - Dashboard", 900, 600);
            }
            return;
        }

        messageLabel.setText("Invalid username or password.");
    }

    @FXML
    private void openRegister() {

        try {

            FXMLLoader loader =
                new FXMLLoader(
                    getClass().getResource(
                        "/view/register.fxml"
                    )
                );

            Parent root = loader.load();

            Stage stage =
                (Stage) usernameField.getScene().getWindow();

            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
            stage.setScene(new Scene(root, bounds.getWidth(), bounds.getHeight()));
            stage.setMaximized(true);
            stage.setTitle("EL7ATY - Register");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void openDashboard(String fxmlPath, String title, int width, int height) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) usernameField.getScene().getWindow();
            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
            stage.setScene(new Scene(root, bounds.getWidth(), bounds.getHeight()));
            stage.setMaximized(true);
            stage.setTitle(title);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}