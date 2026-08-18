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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Customer.Customer;
import Customer.DietaryPreferences;
import Order.MenuCategory;
import Order.MenuItem;
import Staff.Admin;
import Staff.RestaurantDatabase;
import Staff.Role;
import Staff.Staff;
import Staff.Waiter;
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
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(250);

        TableColumn<Object, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionCol.setPrefWidth(400);

        adminDataTableView.getColumns().addAll(nameCol, descriptionCol);
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
                showTableForm(null).ifPresent(table -> {
                    RestaurantDatabase.tables.add(table);
                    handleManageTables(null);
                });
                break;
            case "MENU":
                showMenuItemForm(null).ifPresent(item -> {
                    RestaurantDatabase.menuItems.add(item);
                    handleManageMenu(null);
                });
                break;
            case "CATEGORIES":
                showCategoryForm(null).ifPresent(category -> {
                    RestaurantDatabase.categories.add(category);
                    handleManageCategories(null);
                });
                break;
            case "STAFF":
                showStaffForm(null).ifPresent(staff -> {
                    RestaurantDatabase.staff.add(staff);
                    handleManageStaff(null);
                });
                break;
            case "CUSTOMERS":
                showCustomerForm(null).ifPresent(customer -> {
                    RestaurantDatabase.customers.add(customer);
                    handleManageCustomers(null);
                });
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
            MenuCategory category = (MenuCategory) selected;
            boolean inUse = RestaurantDatabase.menuItems.stream()
                    .anyMatch(item -> item.getCategory() == category);
            if (inUse) {
                showAlert(Alert.AlertType.WARNING, "Category In Use", "Move or delete its menu items before deleting this category.");
                return;
            }
            RestaurantDatabase.categories.remove(category);
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
            showTableForm(table).ifPresent(updated -> {
                table.setTableNumber(updated.getTableNumber());
                table.setCapacity(updated.getCapacity());
                table.setLocation(updated.getLocation());
                table.setStatus(updated.getStatus());
                handleManageTables(null);
            });
            return;
        }

        if (currentMode.equals("MENU")) {
            MenuItem item = (MenuItem) selected;
            showMenuItemForm(item).ifPresent(updated -> {
                item.setName(updated.getName());
                item.setPrice(updated.getPrice());
                item.setDescription(updated.getDescription());
                item.setCategory(updated.getCategory());
                item.setAvailable(updated.isAvailable());
                handleManageMenu(null);
            });
            return;
        }

        if (currentMode.equals("CATEGORIES")) {
            MenuCategory category = (MenuCategory) selected;
            showCategoryForm(category).ifPresent(updated -> {
                category.setName(updated.getName());
                category.setDescription(updated.getDescription());
                handleManageCategories(null);
            });
            return;
        }

        if (currentMode.equals("STAFF")) {
            Staff staff = (Staff) selected;
            showStaffForm(staff).ifPresent(updated -> {
                int index = RestaurantDatabase.staff.indexOf(staff);
                RestaurantDatabase.staff.set(index, updated);
                handleManageStaff(null);
            });
            return;
        }

        if (currentMode.equals("CUSTOMERS")) {
            Customer customer = (Customer) selected;
            showCustomerForm(customer).ifPresent(updated -> {
                int index = RestaurantDatabase.customers.indexOf(customer);
                RestaurantDatabase.customers.set(index, updated);
                handleManageCustomers(null);
            });
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

    private Optional<List<String>> showForm(String title, String[] labels, String[] values, String[] placeholders) {
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText("Enter all required information");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        List<TextField> fields = new ArrayList<>();
        for (int i = 0; i < labels.length; i++) {
            Label label = new Label(labels[i]);
            TextField field = new TextField(values[i]);
            field.setPromptText(placeholders[i]);
            field.setPrefWidth(280);
            fields.add(field);
            grid.add(label, 0, i);
            grid.add(field, 1, i);
        }

        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);
        dialog.setResultConverter(button -> {
            if (button != saveButton) return null;
            List<String> result = new ArrayList<>();
            for (TextField field : fields) result.add(field.getText().trim());
            return result;
        });
        return dialog.showAndWait();
    }

    private Optional<Table> showTableForm(Table existing) {
        String[] labels = {"Table number", "Capacity", "Location (INDOOR/OUTDOOR/VIP/PRIVATE_ROOM)", "Status (AVAILABLE/RESERVED/OCCUPIED)"};
        String[] values = existing == null
                ? new String[]{String.valueOf(nextTableNumber()), "4", "INDOOR", "AVAILABLE"}
                : new String[]{String.valueOf(existing.getTableNumber()), String.valueOf(existing.getCapacity()),
                existing.getLocation().name(), existing.getStatus().name()};
        String[] placeholders = {"Positive whole number", "Positive whole number", "INDOOR", "AVAILABLE"};
        Optional<List<String>> result = showForm(existing == null ? "Add Table" : "Update Table", labels, values, placeholders);
        if (!result.isPresent()) return Optional.empty();

        try {
            int number = Integer.parseInt(result.get().get(0));
            int capacity = Integer.parseInt(result.get().get(1));
            TableType location = TableType.valueOf(result.get().get(2).toUpperCase());
            TableStatus status = TableStatus.valueOf(result.get().get(3).toUpperCase());
            if (number <= 0 || capacity <= 0) throw new IllegalArgumentException();
            for (Table table : RestaurantDatabase.tables) {
                if (table != existing && table.getTableNumber() == number) {
                    showAlert(Alert.AlertType.WARNING, "Duplicate Table", "Table number already exists.");
                    return Optional.empty();
                }
            }
            Table table = new Table(number, capacity, location);
            table.setStatus(status);
            return Optional.of(table);
        } catch (IllegalArgumentException ex) {
            showAlert(Alert.AlertType.ERROR, "Invalid Table", "Use positive numbers and valid location/status values.");
            return Optional.empty();
        }
    }

    private Optional<MenuItem> showMenuItemForm(MenuItem existing) {
        String[] labels = {"Name", "Price (EGP)", "Description", "Category", "Available (true/false)"};
        String[] values = existing == null
                ? new String[]{"", "", "", RestaurantDatabase.categories.isEmpty() ? "" : RestaurantDatabase.categories.get(0).getName(), "true"}
                : new String[]{existing.getName(), String.valueOf(existing.getPrice()), existing.getDescription(),
                existing.getCategory().getName(), String.valueOf(existing.isAvailable())};
        String[] placeholders = {"Item name", "Non-negative number", "Item description", "Existing category name", "true or false"};
        Optional<List<String>> result = showForm(existing == null ? "Add Menu Item" : "Update Menu Item", labels, values, placeholders);
        if (!result.isPresent()) return Optional.empty();
        if (RestaurantDatabase.categories.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "No Categories", "Create a category before adding a menu item.");
            return Optional.empty();
        }

        try {
            String name = result.get().get(0);
            double price = Double.parseDouble(result.get().get(1));
            String availableText = result.get().get(4);
            if (name.isEmpty() || price < 0 || (!availableText.equalsIgnoreCase("true") && !availableText.equalsIgnoreCase("false"))) {
                throw new IllegalArgumentException();
            }
            boolean duplicate = RestaurantDatabase.menuItems.stream().anyMatch(item -> item != existing
                    && item.getName().equalsIgnoreCase(name));
            if (duplicate) {
                showAlert(Alert.AlertType.WARNING, "Duplicate Menu Item", "A menu item with this name already exists.");
                return Optional.empty();
            }
            MenuCategory category = RestaurantDatabase.categories.stream()
                    .filter(item -> item.getName().equalsIgnoreCase(result.get().get(3)))
                    .findFirst().orElseThrow(IllegalArgumentException::new);
            return Optional.of(new MenuItem(existing == null ? nextMenuItemId() : existing.getId(), name, price,
                    result.get().get(2), category, Boolean.parseBoolean(availableText)));
        } catch (IllegalArgumentException ex) {
            showAlert(Alert.AlertType.ERROR, "Invalid Menu Item", "Check the name, price, category, and availability values.");
            return Optional.empty();
        }
    }

    private Optional<MenuCategory> showCategoryForm(MenuCategory existing) {
        String[] labels = {"Name", "Description"};
        String[] values = existing == null ? new String[]{"", ""} : new String[]{existing.getName(), existing.getDescription()};
        String[] placeholders = {"Category name", "Category description"};
        Optional<List<String>> result = showForm(existing == null ? "Add Category" : "Update Category", labels, values, placeholders);
        if (!result.isPresent()) return Optional.empty();
        String name = result.get().get(0);
        if (name.isEmpty() || RestaurantDatabase.categories.stream().anyMatch(category -> category != existing
                && category.getName().equalsIgnoreCase(name))) {
            showAlert(Alert.AlertType.WARNING, "Invalid Category", "Category name is required and must be unique.");
            return Optional.empty();
        }
        return Optional.of(new MenuCategory(existing == null ? nextCategoryId() : existing.getId(), name, result.get().get(1)));
    }

    private Optional<Staff> showStaffForm(Staff existing) {
        String[] labels = {"Username", "Password", "Date of birth (YYYY-MM-DD)", "Role (ADMIN/WAITER)", "Start time", "End time"};
        String[] values = existing == null
                ? new String[]{"", "", "", "WAITER", "09:00 AM", "05:00 PM"}
                : new String[]{existing.getUsername(), "", existing.getDateOfBirth().toString(), existing.getRole().name(),
                existing.getWorkingHours().getStartTime(), existing.getWorkingHours().getEndTime()};
        String[] placeholders = {"Unique username", existing == null ? "At least 6 characters" : "Leave blank to keep current password",
                "YYYY-MM-DD", "ADMIN or WAITER", "e.g. 09:00 AM", "e.g. 05:00 PM"};
        Optional<List<String>> result = showForm(existing == null ? "Add Staff" : "Update Staff", labels, values, placeholders);
        if (!result.isPresent()) return Optional.empty();
        try {
            String username = result.get().get(0);
            String password = result.get().get(1);
            LocalDate date = LocalDate.parse(result.get().get(2));
            Role role = Role.valueOf(result.get().get(3).toUpperCase());
            if (username.isEmpty() || (existing == null && !Customer.validatePassword(password))
                    || (existing != null && !password.isEmpty() && !Customer.validatePassword(password))) {
                throw new IllegalArgumentException();
            }
            boolean duplicate = RestaurantDatabase.staff.stream().anyMatch(staff -> staff != existing
                    && staff.getUsername().equalsIgnoreCase(username));
            if (duplicate) {
                showAlert(Alert.AlertType.WARNING, "Duplicate Username", "A staff username already exists.");
                return Optional.empty();
            }
            String finalPassword = password.isEmpty() && existing != null ? existing.getPassword() : password;
            WorkingHours hours = new WorkingHours(result.get().get(4), result.get().get(5));
            Staff staff = role == Role.ADMIN ? new Admin(username, finalPassword, date, hours) : new Waiter(username, finalPassword, date, hours);
            return Optional.of(staff);
        } catch (IllegalArgumentException ex) {
            showAlert(Alert.AlertType.ERROR, "Invalid Staff", "Check all fields, including password, date, role, and working hours.");
            return Optional.empty();
        }
    }

    private Optional<Customer> showCustomerForm(Customer existing) {
        String[] labels = {"Username", "Password", "Date of birth (YYYY-MM-DD)", "Phone", "Dietary preference"};
        String[] values = existing == null
                ? new String[]{"", "", "", "", "NONE"}
                : new String[]{existing.getUsername(), "", existing.getDateOfBirth().toString(), existing.getPhoneNumber(), existing.getDietaryPreferences().name()};
        String[] placeholders = {"Unique username", existing == null ? "At least 8 chars, letters and numbers" : "Leave blank to keep current password",
                "YYYY-MM-DD", "Egyptian mobile number", "NONE, VEGETARIAN, VEGAN, GLUTEN_FREE, NUT_ALLERGY"};
        Optional<List<String>> result = showForm(existing == null ? "Add Customer" : "Update Customer", labels, values, placeholders);
        if (!result.isPresent()) return Optional.empty();
        try {
            String username = result.get().get(0);
            String password = result.get().get(1);
            LocalDate date = LocalDate.parse(result.get().get(2));
            String phone = result.get().get(3);
            DietaryPreferences diet = DietaryPreferences.valueOf(result.get().get(4).toUpperCase());
            if (username.isEmpty() || !Customer.validatePhoneNumber(phone)
                    || (existing == null && !Customer.validatePassword(password))
                    || (existing != null && !password.isEmpty() && !Customer.validatePassword(password))) {
                throw new IllegalArgumentException();
            }
            boolean duplicate = RestaurantDatabase.customers.stream().anyMatch(customer -> customer != existing
                    && customer.getUsername().equalsIgnoreCase(username));
            if (duplicate) {
                showAlert(Alert.AlertType.WARNING, "Duplicate Username", "A customer username already exists.");
                return Optional.empty();
            }
            String finalPassword = password.isEmpty() && existing != null ? existing.getPassword() : password;
            Customer customer = new Customer(username, finalPassword, date, phone, diet);
            if (existing != null) {
                customer.addBalance(existing.getBalance());
                customer.addLoyaltyPoints(existing.getLoyaltyPoints());
            }
            return Optional.of(customer);
        } catch (IllegalArgumentException ex) {
            showAlert(Alert.AlertType.ERROR, "Invalid Customer", "Check username, password, date, phone, and dietary preference.");
            return Optional.empty();
        }
    }

    private int nextTableNumber() {
        return RestaurantDatabase.tables.stream().mapToInt(Table::getTableNumber).max().orElse(0) + 1;
    }

    private int nextMenuItemId() {
        return RestaurantDatabase.menuItems.stream().mapToInt(MenuItem::getId).max().orElse(0) + 1;
    }

    private int nextCategoryId() {
        return RestaurantDatabase.categories.stream().mapToInt(MenuCategory::getId).max().orElse(0) + 1;
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
