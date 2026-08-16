package controller;

import Customer.Customer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label dobLabel;

    @FXML
    private Label dietLabel;

    @FXML
    private Label loyaltyLabel;



    @FXML
    public void initialize() {

        Customer customer =
            Session.getCurrentCustomer();

        if (customer == null) {
            return;
        }

        welcomeLabel.setText(
            "Welcome, " + customer.getUsername() + "!"
        );

        usernameLabel.setText(
            "Username: " + customer.getUsername()
        );

        phoneLabel.setText(
            "Phone: " + customer.getPhoneNumber()
        );

        dobLabel.setText(
            "Date of Birth: " + customer.getDateOfBirth()
        );

        dietLabel.setText("Dietary Preference: "+ customer.getDietaryPreferences());

        loyaltyLabel.setText(
            String.valueOf(
                customer.getLoyaltyPoints()
            )
        );
    }

    @FXML
    private void logout() {

        Session.logout();

        // We will connect this to Login later.
    }

    @FXML
    private void openReservations() {

        System.out.println("Reserve button clicked!");

        try {

            FXMLLoader loader =
                new FXMLLoader(
                    getClass().getResource(
                        "/view/reservation.fxml"
                    )
                );

            Parent root = loader.load();

            Stage stage =
                (Stage) welcomeLabel
                    .getScene()
                    .getWindow();

            stage.setScene(
                new Scene(root, 1000, 700)
            );

            stage.setTitle(
                "EL7ATY - Reserve a Table"
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    @FXML
    private void openMenu() {

        try {

            FXMLLoader loader =
                new FXMLLoader(
                    getClass().getResource(
                        "/view/menu.fxml"
                    )
                );

            Parent root = loader.load();

            Stage stage =
                (Stage) welcomeLabel
                    .getScene()
                    .getWindow();

            stage.setScene(
                new Scene(root, 1100, 700)
            );

            stage.setTitle(
                "EL7ATY - Menu"
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    private void openReservationsHistory() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/reservationHistory.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(root, 900, 650));
            stage.setTitle("EL7ATY - My Reservations");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}