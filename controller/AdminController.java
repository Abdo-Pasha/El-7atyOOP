package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import Customer.Customer;
import Customer.DietaryPreferences;
import Order.MenuCategory;
import Order.MenuItem;
import Staff.RestaurantDatabase;
import Staff.Role;
import Staff.Staff;
import Staff.WorkingHours;
import Table.Table;
import Table.TableStatus;
import Table.TableType;

public class AdminController {

    @FXML private Label currentSectionLabel;
    @FXML private TableView<Object> adminDataTableView;
    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private TextField staffUsernameField;
    @FXML private PasswordField staffPasswordField;
    @FXML private DatePicker staffDobPicker;
    @FXML private ComboBox<String> staffRoleComboBox;
    @FXML private TextField staffStartTimeField;
    @FXML private TextField staffEndTimeField;

    private String currentMode = "TABLES";

    @FXML
    public void initialize() {
        if (RestaurantDatabase.categories.isEmpty()) {
            RestaurantDatabase.categories.add(new MenuCategory(1, "Main Course", "Main food dishes"));
            RestaurantDatabase.categories.add(new MenuCategory(2, "Beverages", "Cold and hot drinks"));
        }

        if (RestaurantDatabase.menuItems.isEmpty()) {
            MenuCategory mainCourse = RestaurantDatabase.categories.get(0);
            MenuCategory beverages = RestaurantDatabase.categories.get(1);

            RestaurantDatabase.menuItems.add(new MenuItem(1, "Mixed Grill", 450.0, "Charcoal grilled meats", mainCourse, true));
            RestaurantDatabase.menuItems.add(new MenuItem(2, "Shish Tawook", 250.0, "Grilled chicken skewers", mainCourse, true));
            RestaurantDatabase.menuItems.add(new MenuItem(3, "Soda", 50.0, "Canned soda", beverages, true));
        }

        staffRoleComboBox.getItems().setAll("ADMIN", "WAITER");
        staffRoleComboBox.setValue("WAITER");

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
    public void handleManageStaff(ActionEvent event) {
        currentMode = "STAFF";
        currentSectionLabel.setText("Currently Viewing: Staff");
        addButton.setText("Add New Staff");
        deleteButton.setText("Delete Selected Staff");

        adminDataTableView.getColumns().clear();

        TableColumn<Object, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameCol.setPrefWidth(180);

        TableColumn<Object, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleCol.setPrefWidth(120);

        TableColumn<Object, String> dobCol = new TableColumn<>("Date of Birth");
        dobCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        dobCol.setPrefWidth(180);

        TableColumn<Object, String> hoursCol = new TableColumn<>("Working Hours");
        hoursCol.setCellValueFactory(new PropertyValueFactory<>("workingHours"));
        hoursCol.setPrefWidth(220);

        adminDataTableView.getColumns().addAll(usernameCol, roleCol, dobCol, hoursCol);
        adminDataTableView.setItems(FXCollections.observableArrayList(RestaurantDatabase.staff));
    }

    @FXML
    public void handleManageCustomers(ActionEvent event) {
        currentMode = "CUSTOMERS";
        currentSectionLabel.setText("Currently Viewing: Customers");
        addButton.setText("Add New Customer");
        deleteButton.setText("Delete Selected Customer");

        adminDataTableView.getColumns().clear();

        TableColumn<Object, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameCol.setPrefWidth(160);

        TableColumn<Object, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        phoneCol.setPrefWidth(150);

        TableColumn<Object, LocalDate> dobCol = new TableColumn<>("Date of Birth");
        dobCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        dobCol.setPrefWidth(170);

        TableColumn<Object, DietaryPreferences> dietCol = new TableColumn<>("Dietary");
        dietCol.setCellValueFactory(new PropertyValueFactory<>("dietaryPreferences"));
        dietCol.setPrefWidth(150);

        TableColumn<Object, Integer> loyaltyCol = new TableColumn<>("Loyalty");
        loyaltyCol.setCellValueFactory(new PropertyValueFactory<>("loyaltyPoints"));
        loyaltyCol.setPrefWidth(100);

        adminDataTableView.getColumns().addAll(usernameCol, phoneCol, dobCol, dietCol, loyaltyCol);
        adminDataTableView.setItems(FXCollections.observableArrayList(RestaurantDatabase.customers));
    }

    @FXML
    public void handleAddAction(ActionEvent event) {
        switch (currentMode) {
            case "TABLES":
                RestaurantDatabase.tables.add(new Table(RestaurantDatabase.tables.size() + 1, 4, TableType.INDOOR));
                handleManageTables(null);
                showAlert(Alert.AlertType.INFORMATION, "Added", "Dummy table added.");
                break;
            case "MENU":
                MenuCategory defaultCat = RestaurantDatabase.categories.isEmpty()
                        ? new MenuCategory(RestaurantDatabase.categories.size() + 1, "General", "General menu items")
                        : RestaurantDatabase.categories.get(0);
                RestaurantDatabase.menuItems.add(new MenuItem(RestaurantDatabase.menuItems.size() + 1, "New Item", 99.99, "New Description", defaultCat, true));
                handleManageMenu(null);
                showAlert(Alert.AlertType.INFORMATION, "Added", "Dummy menu item added.");
                break;
            case "CATEGORIES":
                RestaurantDatabase.categories.add(new MenuCategory(RestaurantDatabase.categories.size() + 1, "New Category", "User created category"));
                handleManageCategories(null);
                showAlert(Alert.AlertType.INFORMATION, "Added", "Dummy category added.");
                break;
            case "STAFF":
                showAlert(Alert.AlertType.INFORMATION, "Staff Form", "Use the staff creation form below to add a new staff account.");
                handleManageStaff(null);
                break;
            case "CUSTOMERS":
                TextInputDialog newCustomerDialog = new TextInputDialog("NewCustomer");
                newCustomerDialog.setTitle("Add Customer");
                newCustomerDialog.setHeaderText("Create a new customer");
                newCustomerDialog.setContentText("Username:");
                Optional<String> usernameOpt = newCustomerDialog.showAndWait();
                if (usernameOpt.isPresent()) {
                    String username = usernameOpt.get().trim();
                    if (username.isEmpty() || RestaurantDatabase.findCustomerByUsername(username) != null) {
                        showAlert(Alert.AlertType.WARNING, "Invalid Username", "Please enter a unique customer username.");
                        return;
                    }
                    try {
                        Customer customer = new Customer(username, "Customer123", LocalDate.of(2000, 1, 1), "01000000000", DietaryPreferences.NONE);
                        RestaurantDatabase.addCustomer(customer);
                        handleManageCustomers(null);
                        showAlert(Alert.AlertType.INFORMATION, "Added", "Customer added successfully.");
                    } catch (IllegalArgumentException ex) {
                        showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
                    }
                }
                break;
            default:
                break;
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
        } else if (currentMode.equals("STAFF")) {
            RestaurantDatabase.staff.remove(selected);
            handleManageStaff(null);
        } else if (currentMode.equals("CUSTOMERS")) {
            RestaurantDatabase.customers.remove((Customer) selected);
            handleManageCustomers(null);
        }
        showAlert(Alert.AlertType.INFORMATION, "Deleted", "Item deleted successfully.");
    }

    @FXML
    public void handleUpdateAction(ActionEvent event) {
        Object selected = adminDataTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select an item to update.");
            return;
        }

        if (currentMode.equals("TABLES")) {
            Table table = (Table) selected;
            ChoiceDialog<TableStatus> statusDialog = new ChoiceDialog<>(table.getStatus(), List.of(TableStatus.values()));
            statusDialog.setTitle("Update Table Status");
            statusDialog.setHeaderText("Change status for Table " + table.getTableNumber());
            statusDialog.setContentText("Status:");
            statusDialog.showAndWait().ifPresent(table::setStatus);
            handleManageTables(null);
            return;
        }

        if (currentMode.equals("MENU")) {
            MenuItem item = (MenuItem) selected;
            TextInputDialog priceDialog = new TextInputDialog(String.valueOf(item.getPrice()));
            priceDialog.setTitle("Update Menu Item");
            priceDialog.setHeaderText("Edit price for " + item.getName());
            priceDialog.setContentText("Price (EGP):");
            Optional<String> priceResult = priceDialog.showAndWait();
            if (priceResult.isPresent()) {
                try {
                    double newPrice = Double.parseDouble(priceResult.get());
                    item.setPrice(newPrice);
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Price", "Please enter a valid number.");
                    return;
                }
            }
            handleManageMenu(null);
            return;
        }

        if (currentMode.equals("CATEGORIES")) {
            MenuCategory category = (MenuCategory) selected;
            TextInputDialog nameDialog = new TextInputDialog(category.getName());
            nameDialog.setTitle("Update Category");
            nameDialog.setHeaderText("Edit category name");
            nameDialog.setContentText("Category name:");
            Optional<String> nameResult = nameDialog.showAndWait();
            if (nameResult.isPresent() && !nameResult.get().trim().isEmpty()) {
                category.setName(nameResult.get().trim());
            }
            handleManageCategories(null);
            return;
        }

        if (currentMode.equals("STAFF")) {
            Staff staff = (Staff) selected;
            TextInputDialog hoursDialog = new TextInputDialog(staff.getWorkingHours() == null ? "09:00 AM - 05:00 PM" : staff.getWorkingHours().toString());
            hoursDialog.setTitle("Update Working Hours");
            hoursDialog.setHeaderText("Edit working hours for " + staff.getUsername());
            hoursDialog.setContentText("Format: 09:00 AM - 05:00 PM");
            Optional<String> hoursResult = hoursDialog.showAndWait();
            if (hoursResult.isPresent()) {
                String value = hoursResult.get().trim();
                String[] parts = value.split("-");
                if (parts.length == 2) {
                    staff.setWorkingHours(new WorkingHours(parts[0].trim(), parts[1].trim()));
                }
            }
            handleManageStaff(null);
            return;
        }

        if (currentMode.equals("CUSTOMERS")) {
            Customer customer = (Customer) selected;
            TextInputDialog phoneDialog = new TextInputDialog(customer.getPhoneNumber());
            phoneDialog.setTitle("Update Customer");
            phoneDialog.setHeaderText("Edit phone number for " + customer.getUsername());
            phoneDialog.setContentText("Phone:");
            Optional<String> phoneResult = phoneDialog.showAndWait();
            if (phoneResult.isPresent()) {
                String phone = phoneResult.get().trim();
                if (Customer.validatePhoneNumber(phone)) {
                    customer.setPhoneNumber(phone);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Invalid Phone", "Phone number must be a valid Egyptian number.");
                    return;
                }
            }
            handleManageCustomers(null);
            return;
        }

        showAlert(Alert.AlertType.INFORMATION, "Update", "This section has no custom update form yet.");
    }

    @FXML
    public void handleCreateStaffAccount(ActionEvent event) {
        String username = staffUsernameField.getText() == null ? "" : staffUsernameField.getText().trim();
        String password = staffPasswordField.getText() == null ? "" : staffPasswordField.getText().trim();
        LocalDate dob = staffDobPicker.getValue();
        String roleText = staffRoleComboBox.getValue();
        String startTime = staffStartTimeField.getText() == null ? "" : staffStartTimeField.getText().trim();
        String endTime = staffEndTimeField.getText() == null ? "" : staffEndTimeField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || dob == null || roleText == null || startTime.isEmpty() || endTime.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Missing Data", "Please fill in all staff account fields.");
            return;
        }

        if (password.length() < 6) {
            showAlert(Alert.AlertType.WARNING, "Weak Password", "Password must be at least 6 characters long.");
            return;
        }

        Role role = Role.valueOf(roleText);
        WorkingHours workingHours = new WorkingHours(startTime, endTime);

        if (RestaurantDatabase.createStaffAccount(username, password, dob, role, workingHours) == null) {
            showAlert(Alert.AlertType.ERROR, "Account Error", "A staff member with this username already exists.");
            return;
        }

        staffUsernameField.clear();
        staffPasswordField.clear();
        staffDobPicker.setValue(null);
        staffRoleComboBox.setValue("WAITER");
        staffStartTimeField.clear();
        staffEndTimeField.clear();

        showAlert(Alert.AlertType.INFORMATION, "Account Created", "Staff account created successfully.");
    }

    @FXML
    public void logout() {
        Session.logout();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) currentSectionLabel.getScene().getWindow();
            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
            stage.setScene(new Scene(root, bounds.getWidth(), bounds.getHeight()));
            stage.setMaximized(true);
            stage.setTitle("EL7ATY - Login");
        } catch (Exception e) {
            e.printStackTrace();
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