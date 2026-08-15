import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class WaiterController {

    @FXML private FlowPane tablesContainer;
    @FXML private Label selectedTableLabel;
    @FXML private VBox activeOrderItemsContainer;
    @FXML private ComboBox<String> menuItemComboBox;
    @FXML private Label orderTotalLabel;
    @FXML private ComboBox<String> statusComboBox;

    private Table currentlySelectedTable = null;

    private Map<Integer, List<String>> tableOrders = new HashMap<>();
    private Map<Integer, Double> tableTotals = new HashMap<>();

    @FXML
    public void initialize() {
        statusComboBox.getItems().addAll("PLACED", "PREPARING", "SERVED", "PAID");
        selectedTableLabel.setText("Please Select a Table");

        if (RestaurantDatabase.tables.isEmpty()) {
            RestaurantDatabase.tables.add(new Table(1, 2));
            RestaurantDatabase.tables.add(new Table(2, 4));
            RestaurantDatabase.tables.add(new Table(3, 6));
        }

        if (RestaurantDatabase.categories.isEmpty()) {
            RestaurantDatabase.categories.add(new MenuCategory("Main Course"));
            RestaurantDatabase.categories.add(new MenuCategory("Beverages"));
        }

        if (RestaurantDatabase.menuItems.isEmpty()) {
            MenuCategory mainCourse = RestaurantDatabase.categories.get(0);
            MenuCategory beverages = RestaurantDatabase.categories.get(1);

            RestaurantDatabase.menuItems.add(new MenuItem(1, "Mixed Grill", 450.0, "Charcoal grilled meats", mainCourse, true));
            RestaurantDatabase.menuItems.add(new MenuItem(2, "Shish Tawook", 250.0, "Grilled chicken skewers", mainCourse, true));
            RestaurantDatabase.menuItems.add(new MenuItem(3, "Soda", 50.0, "Canned soda", beverages, true));
        }

        for (MenuItem item : RestaurantDatabase.menuItems) {
            menuItemComboBox.getItems().add(item.getName() + " - " + item.getPrice() + " EGP");
        }

        refreshTableView();
    }

    @FXML
    public void refreshTableView() {
        tablesContainer.getChildren().clear();

        for (Table table : RestaurantDatabase.tables) {
            Button tableButton = new Button("Table " + table.getTableNumber() + "\n(" + table.getStatus() + ")");
            tableButton.setPrefSize(120, 80);

            String style = "-fx-font-weight: bold; -fx-border-radius: 5; -fx-background-radius: 5; ";

            if (table.getStatus().contains("OCCUPIED")) {
                style += "-fx-background-color: #ffcccc; ";
            } else {
                style += "-fx-background-color: #ccffcc; ";
            }

            if (currentlySelectedTable != null && currentlySelectedTable.getTableNumber() == table.getTableNumber()) {
                style += "-fx-border-color: #7A1F2B; -fx-border-width: 3px; ";
            } else {
                style += "-fx-border-color: #E0DDD8; -fx-border-width: 1px; ";
            }

            tableButton.setStyle(style);
            tableButton.setOnAction(event -> selectTable(table));
            tablesContainer.getChildren().add(tableButton);
        }
    }

    private void selectTable(Table table) {
        this.currentlySelectedTable = table;
        selectedTableLabel.setText("Table " + table.getTableNumber() + " Selected");

        tableOrders.putIfAbsent(table.getTableNumber(), new ArrayList<>());
        tableTotals.putIfAbsent(table.getTableNumber(), 0.0);

        refreshOrderView();
        refreshTableView();
    }

    @FXML
    public void handleAddItem(ActionEvent event) {
        if (currentlySelectedTable == null) {
            showAlert(Alert.AlertType.WARNING, "No Table Selected", "Please select a table to add items to.");
            return;
        }

        String selection = menuItemComboBox.getValue();
        if (selection != null) {
            int tableId = currentlySelectedTable.getTableNumber();

            String[] parts = selection.split(" - ");
            double price = Double.parseDouble(parts[1].replace(" EGP", ""));

            tableOrders.get(tableId).add("• " + selection);
            tableTotals.put(tableId, tableTotals.get(tableId) + price);

            if (currentlySelectedTable.getStatus().equals("AVAILABLE")) {
                currentlySelectedTable.setStatus("OCCUPIED - PLACED");
                refreshTableView();
            }

            refreshOrderView();
        }
    }

    private void refreshOrderView() {
        activeOrderItemsContainer.getChildren().clear();

        if (currentlySelectedTable != null) {
            int tableId = currentlySelectedTable.getTableNumber();
            List<String> items = tableOrders.get(tableId);

            if (items != null) {
                for (String itemStr : items) {
                    Label lbl = new Label(itemStr);
                    lbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333;");
                    activeOrderItemsContainer.getChildren().add(lbl);
                }
            }
            orderTotalLabel.setText("Total: " + String.format("%.2f", tableTotals.get(tableId)) + " EGP");
        }
    }

    @FXML
    public void handleSeatGuests(ActionEvent event) {
        if (currentlySelectedTable != null) {
            currentlySelectedTable.setStatus("OCCUPIED");
            refreshTableView();
        }
    }

    @FXML
    public void handleClearTable(ActionEvent event) {
        if (currentlySelectedTable != null) {
            currentlySelectedTable.setStatus("AVAILABLE");

            tableOrders.remove(currentlySelectedTable.getTableNumber());
            tableTotals.put(currentlySelectedTable.getTableNumber(), 0.0);

            refreshOrderView();
            refreshTableView();
        }
    }

    @FXML
    public void handleUpdateStatus(ActionEvent event) {
        String newStatus = statusComboBox.getValue();
        if (currentlySelectedTable != null && newStatus != null) {
            currentlySelectedTable.setStatus("OCCUPIED - " + newStatus);
            refreshTableView();
            showAlert(Alert.AlertType.INFORMATION, "Status Updated", "Order status updated to: " + newStatus);
        }
    }

    @FXML
    public void handleCheckout(ActionEvent event) {
        if (currentlySelectedTable != null) {
            double total = tableTotals.getOrDefault(currentlySelectedTable.getTableNumber(), 0.0);

            int newInvoiceId = RestaurantDatabase.invoices.size() + 1;
            RestaurantDatabase.invoices.add(new Invoice(newInvoiceId, total));

            handleClearTable(null);

            showAlert(Alert.AlertType.INFORMATION, "Checkout Complete", "Invoice generated for " + total + " EGP. Table cleared.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #F8F5F0; -fx-border-color: #7A1F2B; -fx-border-width: 2px;");
        dialogPane.lookup(".content.label").setStyle("-fx-font-size: 14px; -fx-text-fill: #333333;");

        Node okButton = dialogPane.lookupButton(ButtonType.OK);
        if (okButton != null) {
            okButton.setStyle("-fx-background-color: #7A1F2B; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5 15;");
        }
        alert.showAndWait();
    }
}