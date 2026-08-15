import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Node;

public class AdminController {

    @FXML private Label currentSectionLabel;
    @FXML private TableView<Object> adminDataTableView;
    @FXML private Button addButton;
    @FXML private Button deleteButton;

    private String currentMode = "TABLES";

    @FXML
    public void initialize() {
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

        handleManageTables(null);
    }

    @FXML
    public void handleManageTables(ActionEvent event) {
        currentMode = "TABLES";
        currentSectionLabel.setText("Currently Viewing: Tables");
        addButton.setText("Add New Table");
        deleteButton.setText("Delete Selected Table");

        adminDataTableView.getColumns().clear();

        TableColumn<Object, Integer> idCol = new TableColumn<>("Table Number");
        idCol.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));
        idCol.setPrefWidth(150);

        TableColumn<Object, Integer> capCol = new TableColumn<>("Capacity");
        capCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        capCol.setPrefWidth(150);

        TableColumn<Object, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(150);

        adminDataTableView.getColumns().addAll(idCol, capCol, statusCol);
        adminDataTableView.setItems(FXCollections.observableArrayList(RestaurantDatabase.tables));
    }

    @FXML
    public void handleManageMenu(ActionEvent event) {
        currentMode = "MENU";
        currentSectionLabel.setText("Currently Viewing: Menu Items");
        addButton.setText("Add New Menu Item");
        deleteButton.setText("Delete Selected Item");

        adminDataTableView.getColumns().clear();

        TableColumn<Object, String> nameCol = new TableColumn<>("Item Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(250);

        TableColumn<Object, Double> priceCol = new TableColumn<>("Price (EGP)");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setPrefWidth(150);

        adminDataTableView.getColumns().addAll(nameCol, priceCol);
        adminDataTableView.setItems(FXCollections.observableArrayList(RestaurantDatabase.menuItems));
    }

    @FXML
    public void handleManageCategories(ActionEvent event) {
        currentMode = "CATEGORIES";
        currentSectionLabel.setText("Currently Viewing: Categories");
        addButton.setText("Add New Category");
        deleteButton.setText("Delete Selected Category");

        adminDataTableView.getColumns().clear();

        TableColumn<Object, String> nameCol = new TableColumn<>("Category Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        nameCol.setPrefWidth(400);

        adminDataTableView.getColumns().add(nameCol);
        adminDataTableView.setItems(FXCollections.observableArrayList(RestaurantDatabase.categories));
    }

    @FXML
    public void handleAddAction(ActionEvent event) {
        if (currentMode.equals("TABLES")) {
            RestaurantDatabase.tables.add(new Table(RestaurantDatabase.tables.size() + 1, 4));
            handleManageTables(null);
            showAlert(Alert.AlertType.INFORMATION, "Added", "Dummy table added.");
        } else if (currentMode.equals("MENU")) {
            MenuCategory defaultCat = RestaurantDatabase.categories.isEmpty() ? new MenuCategory("General") : RestaurantDatabase.categories.get(0);
            RestaurantDatabase.menuItems.add(new MenuItem(RestaurantDatabase.menuItems.size() + 1, "New Item", 99.99, "New Description", defaultCat, true));
            handleManageMenu(null);
            showAlert(Alert.AlertType.INFORMATION, "Added", "Dummy menu item added.");
        } else if (currentMode.equals("CATEGORIES")) {
            RestaurantDatabase.categories.add(new MenuCategory("New Category"));
            handleManageCategories(null);
            showAlert(Alert.AlertType.INFORMATION, "Added", "Dummy category added.");
        }
    }

    @FXML
    public void handleDeleteAction(ActionEvent event) {
        Object selected = adminDataTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select an item to delete.");
            return;
        }

        if (currentMode.equals("TABLES")) {
            RestaurantDatabase.tables.remove((Table) selected);
            handleManageTables(null);
        } else if (currentMode.equals("MENU")) {
            RestaurantDatabase.menuItems.remove((MenuItem) selected);
            handleManageMenu(null);
        } else if (currentMode.equals("CATEGORIES")) {
            RestaurantDatabase.categories.remove((MenuCategory) selected);
            handleManageCategories(null);
        }
        showAlert(Alert.AlertType.INFORMATION, "Deleted", "Item deleted successfully.");
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