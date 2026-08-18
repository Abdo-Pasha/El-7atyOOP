package controller;

import Customer.Customer;
import Reservation.Reservation;
import Reservation.ReservationStatus;
import Reservation.RestaurantSystem;
import Table.Table;

import java.time.LocalDateTime;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ReservationHistoryController {

    @FXML
    private VBox reservationContainer;


    
    @FXML
    public void initialize() {
        displayReservations();
    }

    private void displayReservations() {
        reservationContainer.getChildren().clear();

        Customer customer = Session.getCurrentCustomer();

        if (customer == null) {
            Label message = new Label("Please login first.");
            reservationContainer.getChildren().add(message);
            return;
        }

        boolean found = false;

        for (Reservation reservation : RestaurantSystem.reservations) {

            if (reservation.getCustomer().getUsername().equals(customer.getUsername())) {

                found = true;

                VBox card = createReservationCard(reservation);

                reservationContainer.getChildren().add(card);
            }
        }

        if (!found) {
            Label message = new Label("You have no reservations.");
            message.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
            reservationContainer.getChildren().add(message);
        }
    }

    private VBox createReservationCard(Reservation reservation) {

        VBox card = new VBox(8);

        card.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-border-color: #E0DDD8; -fx-border-radius: 15; -fx-padding: 20;");

        Label idLabel = new Label("Reservation ID: " + reservation.getReservationId());

        Label tableLabel = new Label("Table: " + reservation.getTable().getTableNumber());

        Label dateLabel = new Label("Date & Time: " + reservation.getReservationTime());

        Label partyLabel = new Label("Party Size: " + reservation.getPartySize());

        Label statusLabel = new Label("Status: " + reservation.getStatus());

        idLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        statusLabel.setStyle("-fx-font-weight: bold;");

        card.getChildren().addAll(idLabel, tableLabel, dateLabel, partyLabel, statusLabel);

        if (reservation.getStatus() == ReservationStatus.CONFIRMED) {

            Button cancelButton = new Button("Cancel Reservation");

            cancelButton.setStyle("-fx-background-color: #7A1F2B; -fx-text-fill: white; -fx-font-weight: bold;");

            cancelButton.setOnAction(event -> cancelReservation(reservation));

            card.getChildren().add(cancelButton);
        }

        return card;
    }

    private void cancelReservation(Reservation reservation) {

        Customer customer = Session.getCurrentCustomer();

        RestaurantSystem.cancelReservation(customer, reservation.getReservationId());

        displayReservations();
    }

    @FXML
    private void backToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) reservationContainer.getScene().getWindow();
            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
            stage.setScene(new Scene(root, bounds.getWidth(), bounds.getHeight()));
            stage.setMaximized(true);
            stage.setTitle("EL7ATY - Customer Dashboard");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}