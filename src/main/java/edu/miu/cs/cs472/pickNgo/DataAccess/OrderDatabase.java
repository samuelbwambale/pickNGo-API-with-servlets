package edu.miu.cs.cs472.pickNgo.DataAccess;


import edu.miu.cs.cs472.pickNgo.model.Order;
import edu.miu.cs.cs472.pickNgo.utils.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDatabase {
    static HashMap<String, Order> orders = new HashMap<String, Order>();
    static LocalDate date = LocalDate.now();

    public static Order createOrder(Order order){
        String orderNumber = Utils.generateRandomString(6);
        order.setOrderNumber(orderNumber);
        order.setOrderDate(date);
//        order.addItem();
        orders.put(orderNumber, order);
        return order;
    }

    public static List<Order> getAllOrders() {
        List<Order> allOrders = new ArrayList<Order>();
        for(Map.Entry<String,Order> entry: orders.entrySet())
        {
            allOrders.add(entry.getValue());
        }
        return allOrders;
    }

    public static Order getOrder(String orderNumber) {
        return orders.get(orderNumber);
    }

    public static void deleteOrder(String orderNumber) {
        orders.remove(orderNumber);
    }

    public static void updateOrder(Order order) {
        orders.put(order.getOrderNumber(), order);

    }
}
