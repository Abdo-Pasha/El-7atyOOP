package Order;

public class Invoice {
    static int invoiceId;
    private Order order;
    private double taxRate;
    private double serviceChargeRate;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;


    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public double getServiceChargeRate() {
        return serviceChargeRate;
    }

    public void setServiceChargeRate(double serviceChargeRate) {
        this.serviceChargeRate = serviceChargeRate;
    }

    public double calculateTax() {
        return order.calculateSubtotal() * taxRate;
    }

    public double calculateServiceCharge() {
        return order.calculateSubtotal() * serviceChargeRate;
    }

    public double calculateFinalTotal() {
        return order.calculateSubtotal() + calculateTax() + calculateServiceCharge();
    }

    public void pay(PaymentMethod method) {
        this.paymentMethod = method;
        this.paymentStatus = PaymentStatus.PAID;
        order.setStatus(OrderStatus.PAID);
    }
}
