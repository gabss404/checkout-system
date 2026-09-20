package service;

import model.*;
import java.util.ArrayList;

public class Store {

    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();

    public void registerProduct(String name, double price, int stock) {
        products.add(new Product(name, price, stock));
    }

    public ArrayList<Product> listProducts() {
        return products;
    }

    public ArrayList<Order> listOrders() {
        return orders;
    }

    public Product findProduct(String name) {
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    public Order findOrder(int id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    public Order createOrder(String customer) {
        Order order = new Order(customer);
        orders.add(order);
        return order;
    }

    public void addItemToOrder(Order order, String productName, int quantity) throws Exception {
        Product product = findProduct(productName);
        if (product == null) {
            throw new Exception("Product not found: " + productName);
        }
        if (quantity <= 0) {
            throw new Exception("Quantity must be greater than zero.");
        }

        product.reduceStock(quantity);
        order.addItem(new OrderItem(product, quantity));
    }

    public void applyCoupon(Order order, String code) throws Exception {
        double percentage;
        switch (code.toUpperCase()) {
            case "DISCOUNT10":
                percentage = 0.10;
                break;
            case "DISCOUNT20":
                percentage = 0.20;
                break;
            default:
                throw new Exception("Invalid coupon: " + code);
        }
        order.applyDiscount(percentage);
    }

    public void payOrder(Order order) throws Exception {
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new Exception("Only a PENDING order can be paid.");
        }
        order.setStatus(OrderStatus.PAID);
    }

    public void shipOrder(Order order) throws Exception {
        if (order.getStatus() != OrderStatus.PAID) {
            throw new Exception("Only a PAID order can be shipped.");
        }
        order.setStatus(OrderStatus.SHIPPED);
    }

    public void cancelOrder(Order order) throws Exception {
        if (order.getStatus() == OrderStatus.SHIPPED) {
            throw new Exception("A SHIPPED order cannot be cancelled.");
        }
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new Exception("Order is already cancelled.");
        }

        for (OrderItem item : order.getItems()) {
            item.getProduct().restoreStock(item.getQuantity());
        }

        order.setStatus(OrderStatus.CANCELLED);
    }
}
