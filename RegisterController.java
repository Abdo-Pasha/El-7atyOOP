package controller;

import Customer.Customer;
import Customer.DietaryPreferences;
import Reservation.RestaurantSystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate;
import javafx.scene.control.DatePicker;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private TextField phoneField;

    @FXML
    private ComboBox<DietaryPreferences> dietaryBox;

    @FXML
    private Label messageLabel;


    @FXML
    public void initialize() {

        dietaryBox.getItems().addAll(
            DietaryPreferences.values()
        );

        dietaryBox.setValue(
            DietaryPreferences.NONE
        );
    }

    @FXML
    private void register() {

        String username = usernameField.getText().trim();

        String password = passwordField.getText();

        LocalDate dob = dobPicker.getValue();

        String phone = phoneField.getText().trim();

        DietaryPreferences dietary = dietaryBox.getValue();


        // Basic GUI validation

        if (username.isEmpty() || password.isEmpty() || phone.isEmpty() || dob == null)
        {
            messageLabel.setText("Please fill in all fields.");

            return;
        }


        if (dietary == null) {

            messageLabel.setText("Please select a dietary preference.");

            return;
        }


        try {

            Customer customer = RestaurantSystem.registerCustomer(username, password, dob, phone, dietary);

            if (customer == null) {

                messageLabel.setText(
                    "Username already exists."
                );

                return;
            }


            messageLabel.setText(
                "Registration successful!"
            );

            clearFields();


        } catch (IllegalArgumentException e) {

            messageLabel.setText(
                e.getMessage()
            );
        }
    }

    private void clearFields() {

        usernameField.clear();
        passwordField.clear();
        dobPicker.setValue(null);
        phoneField.clear();

        dietaryBox.setValue(DietaryPreferences.NONE);
    }

    @FXML
    private void backToLogin() {

        try {

            FXMLLoader loader =
                new FXMLLoader(
                    getClass().getResource(
                        "/view/login.fxml"
                    )
                );

            Parent root = loader.load();

            Stage stage =
                (Stage) usernameField
                    .getScene()
                    .getWindow();

            stage.setScene(
                new Scene(root, 450, 500)
            );

            stage.setTitle(
                "EL7ATY - Login"
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}