package edu.miu.cs.cs472.pickNgo.controller;


import com.google.gson.Gson;
import edu.miu.cs.cs472.pickNgo.DataAccess.ItemDatabase;
import edu.miu.cs.cs472.pickNgo.DataAccess.OrderDatabase;
import edu.miu.cs.cs472.pickNgo.model.Item;
import edu.miu.cs.cs472.pickNgo.model.Order;
import edu.miu.cs.cs472.pickNgo.utils.Status;
import edu.miu.cs.cs472.pickNgo.utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "OrderController", urlPatterns = {"/orders"})
public class OrderController extends HttpServlet {

    protected static void processOrderRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Utils.setResponseHeaders(response);
        Gson gson = new Gson();

        try {
            StringBuilder sb = new StringBuilder();
            String s;
            List<String> list = new ArrayList<>();
            while ((s = request.getReader().readLine()) != null) {
                sb.append(s);
            }

            Order order = (Order) gson.fromJson(sb.toString(), Order.class);
            List<Item> selectedItems = new ArrayList<>();
            for(Item item : order.getItems()) {
                System.out.println("item " + item.getItemId());
                String itemId = item.getItemId();

                Item savedItem = ItemDatabase.getItem(itemId);
                selectedItems.add(savedItem);
            }
            order.setItems(selectedItems);
            Order orderDb = OrderDatabase.createOrder(order);
            System.out.println("---order---"+order.toString());
            Status status = new Status(200, "Success", orderDb);

            response.getOutputStream().print(gson.toJson(status));
            response.getOutputStream().flush();
        } catch (Exception ex) {
            ex.printStackTrace();
            Status status = new Status(500, ex.getMessage(), null);
            response.setStatus(500);
            response.getOutputStream().print(gson.toJson(status));
            response.getOutputStream().flush();
        }
    }

    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            setResponseHeaders(response);
            // Your code here...
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        } catch (Throwable e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        }
    }

    public static void setResponseHeaders(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Max-Age", "86400");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setResponseHeaders(response);
        processOrderRequest(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utils.setResponseHeaders(response);
        Gson gson = new Gson();
        String queryString = request.getQueryString();
        if (queryString != null && queryString.contains("orderNumber")) {
            try {
                String orderNumber = request.getParameter("orderNumber");
                Order order = OrderDatabase.getOrder(orderNumber);
                if (order != null) {
                    Status status = new Status(200, "Success", order);
                    response.getOutputStream().print(gson.toJson(status));
                } else {
                    Status status = new Status(404, "Order Not Found", null);
                    response.getOutputStream().print(gson.toJson(status));
                }
                response.getOutputStream().flush();
            }catch (Exception ex){

                ex.printStackTrace();
                Status status = new Status(500, ex.getMessage(), null);
                response.setStatus(500);
                response.getOutputStream().print(gson.toJson(status));
                response.getOutputStream().flush();

            }

        } else{
            try {

                List<Order> selectedOrders = OrderDatabase.getAllOrders();
                Status status = new Status(200, "Success", selectedOrders);
                response.getOutputStream().print(gson.toJson(status));
                response.getOutputStream().flush();
            }catch (Exception ex){

                ex.printStackTrace();
                Status status = new Status(500, ex.getMessage(), null);
                response.setStatus(500);
                response.getOutputStream().print(gson.toJson(status));
                response.getOutputStream().flush();

            }
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Utils.setResponseHeaders(resp);
        Gson gson = new Gson();
        String queryString = req.getQueryString();

        if(queryString != null && queryString.contains("orderNumber")){
            try{
                StringBuilder sb = new StringBuilder();
                String s;
                List<String> list = new ArrayList<>();
                while ((s = req.getReader().readLine()) != null) {
                    sb.append(s);
                }

                String orderNumber = req.getParameter("orderNumber");
                Order savedOrder = OrderDatabase.getOrder(orderNumber);
                if (savedOrder != null) {
                    Order newOrder = (Order) gson.fromJson(sb.toString(), Order.class);

                    List<Item> newSelectedItems = new ArrayList<>();
                    for(Item item : newOrder.getItems()) {
                        String itemId = item.getItemId();
                        Item savedItem = ItemDatabase.getItem(itemId);
                        newSelectedItems.add(savedItem);
                    }
                    newOrder.setItems((ArrayList<Item>) newSelectedItems);


                    if(!newOrder.getItems().equals(savedOrder.getItems())){
                        savedOrder.setItems((ArrayList<Item>) newSelectedItems);
                    }

                    savedOrder.setItems((ArrayList<Item>) newSelectedItems);
                    OrderDatabase.updateOrder(savedOrder);
                    Status status = new Status(200, "Success", newOrder);
                    resp.getOutputStream().print(gson.toJson(status));
                } else {
                    Status status = new Status(404, "Order Not Found", null);
                    resp.getOutputStream().print(gson.toJson(status));
                }
                resp.getOutputStream().flush();
            }catch (Exception ex){
                ex.printStackTrace();
                Status status = new Status(500, ex.getMessage(), null);
                resp.setStatus(500);
                resp.getOutputStream().print(gson.toJson(status));
                resp.getOutputStream().flush();
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Utils.setResponseHeaders(resp);
        Gson gson = new Gson();
        String queryString = req.getQueryString();
        if(queryString != null && queryString.contains("orderNumber")){
            try{
                String orderNumber = req.getParameter("orderNumber");
                Order order = OrderDatabase.getOrder(orderNumber);
                if(order != null){
                    OrderDatabase.deleteOrder(orderNumber);
                    Status status = new Status(200, "Success", null);
                    resp.getOutputStream().print(gson.toJson(status));

                }else{
                    Status status = new Status(404, "Order Not Found", null);
                    resp.getOutputStream().print(gson.toJson(status));
                }
                resp.getOutputStream().flush();
            }catch (Exception e){
                e.printStackTrace();
                Status status = new Status(500, e.getMessage(), null);
                resp.setStatus(500);
                resp.getOutputStream().print(gson.toJson(status));
                resp.getOutputStream().flush();
            }
        }
    }
}

