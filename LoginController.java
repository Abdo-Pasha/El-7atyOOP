package controller;

import Customer.Customer;
import Reservation.RestaurantSystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

            messageLabel.setText(
                "Please enter username and password."
            );

            return;
        }

        Customer customer =
            RestaurantSystem.loginCustomer(
                username,
                password
            );

        if (customer == null) {

            messageLabel.setText(
                "Invalid username or password."
            );

            return;
        }

        // Save the logged-in customer
        Session.setCurrentCustomer(customer);

        messageLabel.setText(
            "Login successful!"
        );

        openDashboard();
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

            stage.setScene(
                new Scene(root, 450, 550)
            );

            stage.setTitle("EL7ATY - Register");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void openDashboard() {

        try {

            FXMLLoader loader =
                new FXMLLoader(
                    getClass().getResource(
                        "/view/dashboard.fxml"
                    )
                );

            Parent root = loader.load();

            Stage stage =
                (Stage) usernameField.getScene().getWindow();

            stage.setScene(
                new Scene(root, 900, 600)
            );

            stage.setTitle("EL7ATY - Dashboard");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}