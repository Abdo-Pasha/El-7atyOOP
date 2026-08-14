package controller;

import Order.Invoice;
import Order.MenuCategory;
import Order.MenuItem;
import Order.Order;
import Order.OrderItem;
import Order.OrderStatus;
import Order.PaymentMethod;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class CheckoutController {

    @FXML
    private VBox orderContainer;

    @FXML
    private Label subtotalLabel;

    @FXML
    private Label taxLabel;

    @FXML
    private Label serviceChargeLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private RadioButton cashRadio;

    @FXML
    private RadioButton creditCardRadio;

    @FXML
    private RadioButton debitCardRadio;

    @FXML
    private RadioButton walletRadio;

    @FXML
    private Label feedbackLabel;

	@FXML
	private Button startPreparingButton;

	@FXML
	private Button markServedButton;

    private Order order;

    private Invoice invoice;

    @FXML
    public void initialize() {
        setupPaymentMethods();
    }

    private void setupPaymentMethods() {

        ToggleGroup paymentGroup = new ToggleGroup();

        cashRadio.setToggleGroup(paymentGroup);
        creditCardRadio.setToggleGroup(paymentGroup);
        debitCardRadio.setToggleGroup(paymentGroup);
        walletRadio.setToggleGroup(paymentGroup);

        cashRadio.setSelected(true);
    }

    private void displayOrder() {

        orderContainer.getChildren().clear();

        for (OrderItem item : order.getItems()) {

            Label label =
                    new Label(
                            item.getMenuItem().getName()
                            + " x "
                            + item.getQuantity()
                            + " = "
                            + String.format(
                                    "%.2f EGP",
                                    item.getTotal()
                            )
                    );

            orderContainer.getChildren().add(label);
        }
    }

    private void calculateTotals() {

        double subtotal =
                order.calculateSubtotal();

        double tax =
                invoice.calculateTax();

        double serviceCharge =
                invoice.calculateServiceCharge();

        double total =
                invoice.calculateFinalTotal();

        subtotalLabel.setText(
                String.format(
                        "Subtotal: %.2f EGP",
                        subtotal
                )
        );

        taxLabel.setText(
                String.format(
                        "Tax: %.2f EGP",
                        tax
                )
        );

        serviceChargeLabel.setText(
                String.format(
                        "Service Charge: %.2f EGP",
                        serviceCharge
                )
        );

        totalLabel.setText(
                String.format(
                        "Total: %.2f EGP",
                        total
                )
        );
    }

    @FXML
	private void confirmPayment() {

    	if (order.getStatus() != OrderStatus.SERVED) {

        	feedbackLabel.setText(
                "Order must be served before payment."
        	);

        	return;
    	}

    	PaymentMethod method;

    	if (cashRadio.isSelected()) {

        	method = PaymentMethod.CASH;

    	} else if (creditCardRadio.isSelected()) {

        	method = PaymentMethod.CREDIT_CARD;

    	} else if (debitCardRadio.isSelected()) {

        	method = PaymentMethod.DEBIT_CARD;

    	} else {

        	method = PaymentMethod.DIGITAL_WALLET;
    	}

    	invoice.pay(method);

    	updateStatusDisplay();

    	feedbackLabel.setText(
            "Payment successful!"
    	);
	}

    public void setOrder(Order order) {

        this.order = order;

        invoice = new Invoice(order);

        invoice.setTaxRate(0.14);
        invoice.setServiceChargeRate(0.10);

        displayOrder();

        calculateTotals();

		updateStatusDisplay();
    }

	private void updateStatusDisplay() {

    	statusLabel.setText(
            "Status: " + order.getStatus()
    	);

    	startPreparingButton.setDisable(
            order.getStatus() != OrderStatus.PLACED
    	);

    	markServedButton.setDisable(
            order.getStatus() != OrderStatus.PREPARING
    	);
	}

	@FXML
	private void startPreparing() {

   		if (order.getStatus() == OrderStatus.PLACED) {

        	order.setStatus(OrderStatus.PREPARING);
        	updateStatusDisplay();
    	}
	}

	@FXML
	private void markServed() {

    	if (order.getStatus() == OrderStatus.PREPARING) {

        	order.setStatus(OrderStatus.SERVED);
        	updateStatusDisplay();
    	}
	}

}