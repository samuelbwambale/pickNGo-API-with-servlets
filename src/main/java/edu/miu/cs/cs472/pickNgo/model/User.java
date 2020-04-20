package edu.miu.cs.cs472.pickNgo.model;

import java.util.List;

public class User {
    private String id;
    private String username;
    private String password;
    private String type;
    //private Order order;


    public User(String id, String username, String password, String type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
        //this.order = order;
    }
//    public void signup(String id, String password, String name, String type) {
//        /* to be implemented */
//
//    }

//    public boolean login(String userName, String password) {
//        /* to be implemented */
//        if (this.username.equals(userName) && this.password.equals(password)){
//            return true;
//        }
//        else return false;
//    }

    public void logout() {
        /*to be implemented*/

    }

//    public void addOrderItem(Item item) {
//        this.order.addItem(item);
//    }
//
//    public void removeOrderItem(Item item) {
//        this.order.removeItem(item);
//    }
//
//    public List<Item> placeOrder()  {
//        return order.getItems();
//    }


//    public void signup(String id, String name, String password) {
//
//    }

//    public Order viewOrder() {
//
//    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + username + '\'' +
                '}';
    }

    public void setUserId(String userId) {
        this.id =userId;
    }

    public String getUserId() {
        return this.id;
    }

}
