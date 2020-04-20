package edu.miu.cs.cs472.pickNgo.model;

import java.util.List;

public class Customer extends User {

    private Order order;
    public Customer(String id, String name, String password, String type) {
        super(id, name, password,type);
        this.order = null;
    }



    public void addOrderItem(Item item) {
        this.order.addItem(item);
    }

    public void removeOrderItem(Item item) {
        this.order.removeItem(item);
    }

    public List<Item> placeOrder()  {
        return order.getItems();
    }
}
