package app;

import model.*;
import service.Store;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Store store = new Store();

        store.registerProduct("Mechanical Keyboard", 250.00, 10);
        store.registerProduct("Gaming Mouse", 120.00, 15);
        store.registerProduct("24in Monitor", 800.00, 5);

        int option = -1;

        while (option != 0) {
            showMenu();

            try {
                option = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Please enter numbers only.");
                scanner.nextLine();
                option = -1;
                continue;
            }

            switch (option) {
                case 1:
                    listProducts(store);
                    break;
                case 2:
                    createOrder(scanner, store);
                    break;
                case 3:
                    addItem(scanner, store);
                    break;
                case 4:
                    applyCoupon(scanner, store);
                    break;
                case 5:
                    payOrder(scanner, store);
                    break;
                case 6:
                    shipOrder(scanner, store);
                    break;
                case 7:
                    cancelOrder(scanner, store);
                    break;
                case 8:
                    listOrders(store);
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n===== CHECKOUT SYSTEM =====");
        System.out.println("1 - List products");
        System.out.println("2 - Create order");
        System.out.println("3 - Add item to order");
        System.out.println("4 - Apply discount coupon");
        System.out.println("5 - Pay order");
        System.out.println("6 - Ship order");
        System.out.println("7 - Cancel order");
        System.out.println("8 - List orders");
        System.out.println("0 - Exit");
        System.out.print("Choose: ");
    }

    private static void listProducts(Store store) {
        ArrayList<Product> products = store.listProducts();
        for (Product p : products) {
            System.out.println(p);
        }
    }

    private static void listOrders(Store store) {
        ArrayList<Order> orders = store.listOrders();
        if (orders.isEmpty()) {
            System.out.println("No orders created yet.");
            return;
        }
        for (Order o : orders) {
            System.out.println(o);
            for (OrderItem item : o.getItems()) {
                System.out.println("   " + item);
            }
        }
    }

    private static void createOrder(Scanner scanner, Store store) {
        System.out.print("Customer name: ");
        String customer = scanner.nextLine();
        Order order = store.createOrder(customer);
        System.out.println("Order created! ID: " + order.getId());
    }

    private static Order askForOrderById(Scanner scanner, Store store) {
        System.out.print("Order ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Order order = store.findOrder(id);
        if (order == null) {
            System.out.println("Order not found.");
        }
        return order;
    }

    private static void addItem(Scanner scanner, Store store) {
        Order order = askForOrderById(scanner, store);
        if (order == null) return;

        System.out.print("Product name: ");
        String product = scanner.nextLine();
        System.out.print("Quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        try {
            store.addItemToOrder(order, product, quantity);
            System.out.println("Item added!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void applyCoupon(Scanner scanner, Store store) {
        Order order = askForOrderById(scanner, store);
        if (order == null) return;

        System.out.print("Coupon code: ");
        String code = scanner.nextLine();

        try {
            store.applyCoupon(order, code);
            System.out.println("Coupon applied!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void payOrder(Scanner scanner, Store store) {
        Order order = askForOrderById(scanner, store);
        if (order == null) return;
        try {
            store.payOrder(order);
            System.out.println("Order paid!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void shipOrder(Scanner scanner, Store store) {
        Order order = askForOrderById(scanner, store);
        if (order == null) return;
        try {
            store.shipOrder(order);
            System.out.println("Order shipped!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void cancelOrder(Scanner scanner, Store store) {
        Order order = askForOrderById(scanner, store);
        if (order == null) return;
        try {
            store.cancelOrder(order);
            System.out.println("Order cancelled!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
