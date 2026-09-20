package model;

import java.util.ArrayList;

public class Order {

    private static int counter = 1;

    private int id;
    private String customer;
    private ArrayList<OrderItem> items = new ArrayList<>();
    private OrderStatus status = OrderStatus.PENDING;
    private double discountPercentage = 0;

    public Order(String customer) {
        this.id = counter++;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public ArrayList<OrderItem> getItems() {
        return items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public void applyDiscount(double percentage) {
        this.discountPercentage = percentage;
    }

    public double calculateTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getSubtotal();
        }
        return total - (total * discountPercentage);
    }

    public void setStatus(OrderStatus newStatus) {
        this.status = newStatus;
    }

    @Override
    public String toString() {
        return "Order #" + id + " - " + customer + " - " + status +
                " - Total: $" + String.format("%.2f", calculateTotal());
    }
}
