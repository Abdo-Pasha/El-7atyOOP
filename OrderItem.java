package Order;

public class OrderItem {
    private MenuItem menuItem;
    private int quantity;
    private String notes;
    private OrderStatus status;
    
    public OrderItem() {
    }

    public OrderItem(MenuItem menuItem, int quantity, String notes) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.notes = notes;
        this.status = OrderStatus.PLACED;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getTotal()
    {
        return this.getQuantity() * this.getMenuItem().getPrice();
    }

    public void increaseQuantity()
    {
        this.setQuantity(this.getQuantity() + 1);
    }

    public void decreaseQuantity()
    {
        this.setQuantity(this.getQuantity() - 1);
    }

}
