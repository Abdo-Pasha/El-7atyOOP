package controller;

import Customer.Customer;
import Reservation.Reservation;
import Reservation.RestaurantSystem;
import Table.Table;
import Table.TableStatus;
import Table.TableType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReservationController {

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> timeBox;

    @FXML
    private Spinner<Integer> partySpinner;

    @FXML
    private ComboBox<TableType> locationBox;

    @FXML
    private FlowPane tableContainer;

    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {

        // Party size: 1 to 20 people
        partySpinner.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(
                1,
                20,
                2
            )
        );


        // Restaurant time slots
        timeBox.getItems().addAll(
            "12:00",
            "13:00",
            "14:00",
            "15:00",
            "16:00",
            "17:00",
            "18:00",
            "19:00",
            "20:00",
            "21:00",
            "22:00"
        );


        // Table locations
        locationBox.getItems().addAll(
            TableType.values()
        );

        // Default date
        datePicker.setValue(
            LocalDate.now()
        );

        // Initially show all tables
        displayTables(
            RestaurantSystem.getTables()
        );
    }

    @FXML
    private void findTables() {

        tableContainer.getChildren().clear();

        LocalDate date = datePicker.getValue();
        String time = timeBox.getValue();
        int partySize = partySpinner.getValue();
        TableType location = locationBox.getValue();

        // Validate date
        if (date == null) {
            messageLabel.setText("Please select a date.");
            return;
        }

        // Validate time
        if (time == null) {
            messageLabel.setText("Please select a time.");
            return;
        }

        // Convert selected time to LocalDateTime
        LocalTime localTime = LocalTime.parse(time);

        LocalDateTime requestTime =
            LocalDateTime.of(date, localTime);

        // Ask the BACKEND for available tables
        List<Table> availableTables =
            RestaurantSystem.viewAvailableTables(
                requestTime,
                partySize
            );

        // Apply location filter
        for (Table table : availableTables) {

            if (location != null
                    && table.getLocation() != location) {
                continue;
            }

            displayTable(table);
        }

        if (tableContainer.getChildren().isEmpty()) {

            messageLabel.setText(
                "No available tables match your requirements."
            );

        } else {

            messageLabel.setText(
                "Available tables found."
            );
        }
    }

    private void displayTables(Table[] tables) {

        tableContainer
            .getChildren()
            .clear();

        for (Table table : tables) {

            // Skip empty positions in the array
            if (table == null) {
                continue;
            }

            if (table.getStatus()
                    == TableStatus.AVAILABLE) {

                displayTable(table);
            }
        }
    }

    private void displayTable(Table table) {

        VBox card =
            new VBox(10);

        card.setPrefWidth(200);

        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 15;" +
            "-fx-border-color: #E0DDD8;" +
            "-fx-border-radius: 15;" +
            "-fx-padding: 20;"
        );


        Label tableLabel =
            new Label(
                "Table " +
                table.getTableNumber()
            );

        tableLabel.setStyle(
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;"
        );


        Label capacityLabel =
            new Label(
                "Capacity: " +
                table.getCapacity()
            );


        Label locationLabel =
            new Label(
                "Location: " +
                table.getLocation()
            );


        Label statusLabel =
            new Label(
                "Status: " +
                table.getStatus()
            );


        Button selectButton =
            new Button("SELECT");

        selectButton.setMaxWidth(
            Double.MAX_VALUE
        );


        selectButton.setStyle(
            "-fx-background-color: #7A1F2B;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;"
        );


        selectButton.setOnAction(
            event -> selectTable(table)
        );


        card.getChildren().addAll(
            tableLabel,
            capacityLabel,
            locationLabel,
            statusLabel,
            selectButton
        );


        tableContainer
            .getChildren()
            .add(card);
    }

    private void selectTable(Table table) {
        Customer customer = Session.getCurrentCustomer();

        if (customer == null) {
            messageLabel.setText("Please login first.");
            return;
        }

        LocalDate date = datePicker.getValue();
        String time = timeBox.getValue();
        int partySize = partySpinner.getValue();

        if (date == null) {
            messageLabel.setText("Please select a date.");
            return;
        }

        if (time == null) {
            messageLabel.setText("Please select a time.");
            return;
        }

        LocalTime localTime = LocalTime.parse(time);
        LocalDateTime reservationTime = LocalDateTime.of(date, localTime);

        Reservation reservation = RestaurantSystem.makeReservation(customer, table, reservationTime, partySize);

        if (reservation != null) {
            messageLabel.setText("Reservation successful! Table " + table.getTableNumber() + " has been reserved.");
            findTables();
        } else {
            messageLabel.setText("Reservation failed. The table may no longer be available.");
        }
    }

    @FXML
    private void backToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) datePicker.getScene().getWindow();
            stage.setScene(new Scene(root, 900, 600));
            stage.setTitle("EL7ATY - Customer Dashboard");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}