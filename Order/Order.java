package Order;

import java.util.ArrayList;

public class Order {
    private int orderId;
    private ArrayList<OrderItem> items = new ArrayList<>();
    private OrderStatus status;
    // private Customer customer;

    public Order() {
        this.status = OrderStatus.PLACED;
        this.items = new ArrayList<>();
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void addItem(MenuItem item, int quantity, String notes)
    {
        if(!item.isAvailable())
        {
            // error
            return;
        }
        
        if (quantity <= 0 )
        {
            // error
            return;
        }
            
           
        OrderItem orderItem = new OrderItem(item, quantity, notes);
        items.add(orderItem);
    }

    public void removeItem(MenuItem item)
    {
        items.remove(item);
    }

    public void updateQuantity(MenuItem item, int quantity)
    {
        if (quantity < 0)
        {
            // error
            return;
        }

        for (OrderItem orderItem : items)
        {
            if (orderItem.getMenuItem() == item)
            {
                if (quantity == 0)
                {
                    items.remove(orderItem);
                }
                else
                {
                    orderItem.setQuantity(quantity);

                    return;
                }
            }
        }
    }

    public double calculateSubtotal()
    {
        double subtotal = 0;

        for (OrderItem order : items)
        {
            subtotal += order.getTotal();
        }

        return subtotal;
    }

    public ArrayList<OrderItem> getItems() {
    return items;
}





}
