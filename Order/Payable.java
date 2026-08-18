package Order;

public interface Payable {

    double calculateSubtotal();

    double calculateTax();

    double calculateServiceCharge();

    double calculateFinalTotal();

    void pay(PaymentMethod paymentMethod);
}
