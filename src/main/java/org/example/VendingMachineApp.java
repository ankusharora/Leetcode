package org.example;

import java.util.*;

// Enum for product type
enum ProductType {
    SNACK, DRINK;
}

// Class representing a product
class Product {
    private String name;
    private double price;
    private ProductType type;

    public Product(String name, double price, ProductType type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public ProductType getType() {
        return type;
    }

    @Override
    public String toString() {
        return name + " (" + type + ") - $" + price;
    }
}

// Class for inventory management
class Inventory {
    private Map<Product, Integer> stock;

    public Inventory() {
        stock = new HashMap<>();
    }

    public void addProduct(Product product, int quantity) {
        stock.put(product, stock.getOrDefault(product, 0) + quantity);
    }

    public boolean isAvailable(Product product) {
        return stock.getOrDefault(product, 0) > 0;
    }

    public void dispenseProduct(Product product) {
        if (isAvailable(product)) {
            stock.put(product, stock.get(product) - 1);
        } else {
            throw new IllegalStateException("Product out of stock");
        }
    }

    public Map<Product, Integer> getStock() {
        return Collections.unmodifiableMap(stock);
    }
}

// Class for managing payments
class PaymentProcessor {
    private double currentBalance;

    public PaymentProcessor() {
        currentBalance = 0.0;
    }

    public void insertMoney(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        currentBalance += amount;
    }

    public boolean canAfford(double price) {
        return currentBalance >= price;
    }

    public void deduct(double amount) {
        if (currentBalance < amount) {
            throw new IllegalStateException("Insufficient balance");
        }
        currentBalance -= amount;
    }

    public double refund() {
        double refundAmount = currentBalance;
        currentBalance = 0.0;
        return refundAmount;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }
}

// Vending Machine class
class VendingMachine {
    private Inventory inventory;
    private PaymentProcessor paymentProcessor;

    public VendingMachine() {
        inventory = new Inventory();
        paymentProcessor = new PaymentProcessor();
    }

    public void addProduct(Product product, int quantity) {
        inventory.addProduct(product, quantity);
    }

    public void insertMoney(double amount) {
        paymentProcessor.insertMoney(amount);
        System.out.println("Balance: $" + paymentProcessor.getCurrentBalance());
    }

    public void selectProduct(Product product) {
        if (!inventory.isAvailable(product)) {
            System.out.println("Sorry, " + product.getName() + " is out of stock.");
            return;
        }

        if (!paymentProcessor.canAfford(product.getPrice())) {
            System.out.println("Insufficient funds. Please add more money.");
            return;
        }

        inventory.dispenseProduct(product);
        paymentProcessor.deduct(product.getPrice());
        System.out.println("Dispensed: " + product.getName());
        System.out.println("Remaining balance: $" + paymentProcessor.getCurrentBalance());
    }

    public void displayProducts() {
        System.out.println("Available products:");
        for (Map.Entry<Product, Integer> entry : inventory.getStock().entrySet()) {
            System.out.println(entry.getKey() + " - Quantity: " + entry.getValue());
        }
    }

    public void refund() {
        double refundedAmount = paymentProcessor.refund();
        System.out.println("Refunded: $" + refundedAmount);
    }
}

// Main class to test the vending machine
public class VendingMachineApp {
    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachine();

        // Add products
        Product chips = new Product("Chips", 1.50, ProductType.SNACK);
        Product soda = new Product("Soda", 1.25, ProductType.DRINK);
        Product chocolate = new Product("Chocolate", 2.00, ProductType.SNACK);

        vendingMachine.addProduct(chips, 10);
        vendingMachine.addProduct(soda, 5);
        vendingMachine.addProduct(chocolate, 2);

        // Simulate user interaction
        vendingMachine.displayProducts();

        vendingMachine.insertMoney(5.00);
        vendingMachine.selectProduct(chips);
        vendingMachine.selectProduct(soda);

        vendingMachine.displayProducts();

        vendingMachine.refund();
    }
}

