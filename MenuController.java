package controller;

import controller.CheckoutController;

import Order.MenuCategory;
import Order.MenuItem;
import Order.Order;
import Order.OrderItem;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class MenuController {

    @FXML
    private FlowPane menuContainer;

    @FXML
    private VBox orderContainer;

    @FXML
    private Label subtotalLabel;

    @FXML
    private Button checkoutButton;

    @FXML
    private void showAll() {
        filterByCategory("All");
    }

    @FXML
    private void showMainCourse() {
        filterByCategory("Main Course");
    }

    @FXML
    private void showPizza() {

        filterByCategory("Pizza");
    }

    @FXML
    private void showSides() {

        filterByCategory("Sides");
    }

    @FXML
    private void showDrinks() {

        filterByCategory("Drinks");
    }

    @FXML
    private void showDesserts() {

        filterByCategory("Desserts");
    }

    private Order currentOrder;

    private List<MenuItem> menuItems = new ArrayList<>();

    @FXML
    public void initialize() {

        currentOrder = new Order();

        loadMenu();
    }

    private void loadMenu() {

        MenuCategory mainCourse =
                new MenuCategory(
                        1,
                        "Main Course",
                        "Main meals"
                );

        MenuCategory drinks =
                new MenuCategory(
                        2,
                        "Drinks",
                        "Cold drinks"
                );

        MenuItem chickenMeal =
                new MenuItem(
                        1,
                        "Chicken Meal",
                        250.0,
                        "Grilled chicken served with fries",
                        mainCourse,
                        true
                );

        MenuItem beefBurger =
                new MenuItem(
                        2,
                        "Beef Burger",
                        200.0,
                        "Beef burger served with fries",
                        mainCourse,
                        true
                );

        MenuItem cola =
                new MenuItem(
                        3,
                        "Cola",
                        45.0,
                        "Cold soft drink",
                        drinks,
                        true
                );

        MenuItem unavailableItem =
                new MenuItem(
                        4,
                        "Special Meal",
                        300.0,
                        "Special restaurant meal",
                        mainCourse,
                        false
                );

        menuItems.add(chickenMeal);
        menuItems.add(beefBurger);
        menuItems.add(cola);
        menuItems.add(unavailableItem);

        displayMenuItems(menuItems);
    }

    private void displayMenuItems(List<MenuItem> items) {

        menuContainer.getChildren().clear();

        for (MenuItem item : items) {
            displayMenuItem(item);
        }
    }

    private void displayMenuItem(MenuItem item) {

    // Main card
        VBox card = new VBox(10);

        card.setPrefWidth(260);
        card.setMaxWidth(260);

        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 15;" +
            "-fx-border-radius: 15;" +
            "-fx-border-color: #E0DDD8;" +
            "-fx-padding: 15;"
        );

    // ================= IMAGE =================

        ImageView imageView = new ImageView();

        try {

            Image image = new Image(
                getClass()
                    .getResourceAsStream(
                        "/images/" + getImageName(item)
                    )
            );

        imageView.setImage(image);

        } catch (Exception e) {

            System.out.println(
                "Could not load image for: " + item.getName()
            );
        }

        imageView.setFitWidth(230);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(false);

    // ================= NAME =================

        Label nameLabel =
         new Label(item.getName());

        nameLabel.setStyle(
            "-fx-font-size: 19px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #333333;"
        );

    // ================= DESCRIPTION =================

        Label descriptionLabel =
        new Label(item.getDescription());

    descriptionLabel.setWrapText(true);

    descriptionLabel.setStyle(
        "-fx-font-size: 13px;" +
        "-fx-text-fill: #666666;"
    );

    // ================= PRICE =================

        Label priceLabel =
            new Label(
                String.format(
                "%.2f EGP",
                    item.getPrice()
                )
            );

        priceLabel.setStyle(
            "-fx-font-size: 17px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #7A1F2B;"
        );

    // ================= AVAILABILITY =================

        Label availabilityLabel =
            new Label(
                item.isAvailable()
                    ? "● Available"
                    : "● Unavailable"
            );

        availabilityLabel.setStyle(
            item.isAvailable()
                ? "-fx-text-fill: #2E7D32; -fx-font-weight: bold;"
                : "-fx-text-fill: #C62828; -fx-font-weight: bold;"
        );

    // ================= BUTTON =================

        Button addButton =
            new Button("Add to Order");

        addButton.setMaxWidth(Double.MAX_VALUE);

        addButton.setStyle(
            "-fx-background-color: #7A1F2B;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 10;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        );

        addButton.setDisable(!item.isAvailable());

        addButton.setOnAction(event -> {
            addItemToOrder(item);
        });

    // ================= ADD EVERYTHING =================

        card.getChildren().addAll(
            imageView,
            nameLabel,
            descriptionLabel,
            priceLabel,
            availabilityLabel,
            addButton
        );

        menuContainer.getChildren().add(card);
    }

    private String getImageName(MenuItem item) {

        switch (item.getName()) {

            case "Chicken Meal":
                return "chicken-meal.jpg";

            case "Beef Burger":
                return "beef-burger.jpg";

            case "Cola":
                return "cola.jpg";

            case "Special Meal":
                return "special-meal.jpg";

            default:
                return "default.jpg";
        }
    }

    private void filterByCategory(String categoryName) {

        if (categoryName.equals("All")) {

            displayMenuItems(menuItems);

            return;
        }

        List<MenuItem> filteredItems = new ArrayList<>();

        for (MenuItem item : menuItems) {

            if (item.getCategory().getName().equals(categoryName)) {

                filteredItems.add(item);
            }
        }

        displayMenuItems(filteredItems);
    }

    private void addItemToOrder(MenuItem item) {

        for (var orderItem : currentOrder.getItems()) {

            if (orderItem.getMenuItem() == item) {

                orderItem.increaseQuantity();

             refreshOrderDisplay();

                return;
        }
    }

    currentOrder.addItem(item, 1, "");

    refreshOrderDisplay();
}

    private void refreshOrderDisplay() {

        orderContainer.getChildren().clear();

        for (var orderItem : currentOrder.getItems()) {

            VBox card = createOrderItemCard(orderItem);

            orderContainer.getChildren().add(card);
        }

        subtotalLabel.setText(
            String.format(
                    "Subtotal: %.2f EGP",
                    currentOrder.calculateSubtotal()
            )
        );
    }

    private VBox createOrderItemCard(OrderItem orderItem) {

        VBox card = new VBox(8);

        Label nameLabel =
            new Label(
                    orderItem.getMenuItem().getName()
            );

        Label priceLabel =
            new Label(
                    String.format(
                            "%.2f EGP",
                            orderItem.getMenuItem().getPrice()
                    )
            );

        Label quantityLabel =
            new Label(
                    String.valueOf(
                            orderItem.getQuantity()
                    )
            );

        Button minusButton =
            new Button("-");

        Button plusButton =
            new Button("+");

        Button removeButton =
            new Button("Remove");

        Label totalLabel =
            new Label(
                    String.format(
                            "Total: %.2f EGP",
                            orderItem.getTotal()
                    )
            );

        HBox quantityBox =
            new HBox(
                    10,
                    minusButton,
                    quantityLabel,
                    plusButton
            );

        plusButton.setOnAction(event -> {

            orderItem.increaseQuantity();

            refreshOrderDisplay();
        });

        minusButton.setOnAction(event -> {

            if (orderItem.getQuantity() > 1) {

                orderItem.decreaseQuantity();

                refreshOrderDisplay();
            }
        });

        removeButton.setOnAction(event -> {

            currentOrder.getItems().remove(orderItem);

            refreshOrderDisplay();
        });

        card.getChildren().addAll(
            nameLabel,
            priceLabel,
            quantityBox,
            totalLabel,
            removeButton
        );

        return card;
    }

    @FXML
    private void openCheckout() throws Exception {

        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/view/checkout.fxml")
        );

        Parent root = loader.load();

        CheckoutController controller =
            loader.getController();

        controller.setOrder(currentOrder);

        Stage stage = new Stage();

        stage.setTitle("Checkout");
        stage.setScene(new Scene(root, 500, 600));

        stage.show();
    }



}