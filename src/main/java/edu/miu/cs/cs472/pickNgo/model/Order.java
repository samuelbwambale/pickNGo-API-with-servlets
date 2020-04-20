package edu.miu.cs.cs472.pickNgo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderNumber;
    private LocalDate orderDate;
    private List<Item> items;

    public Order(String orderNumber) {
        this.orderNumber = orderNumber;
        this.orderDate = LocalDate.now();
        this.items = new ArrayList<>();

    }

    public void addItem(Item item) {
        this.items.add(item);

    }

    public void removeItem(Item item) {
        this.items.remove((Item) item);
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
}
