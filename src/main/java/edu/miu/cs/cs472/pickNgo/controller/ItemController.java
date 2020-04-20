package edu.miu.cs.cs472.pickNgo.controller;

import com.google.gson.Gson;
import edu.miu.cs.cs472.pickNgo.DataAccess.ItemDatabase;
import edu.miu.cs.cs472.pickNgo.model.Item;
import edu.miu.cs.cs472.pickNgo.utils.Status;
import edu.miu.cs.cs472.pickNgo.utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ItemsController", urlPatterns = {"/items"})
public class ItemController extends HttpServlet {

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Utils.setResponseHeaders(response);
        Gson gson = new Gson();

        try {

            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = request.getReader().readLine()) != null) {
                sb.append(s);
            }

            Item item = (Item) gson.fromJson(sb.toString(), Item.class);
            Item savedItem = ItemDatabase.createItem(item);
            Status status = new Status(200, "Success", savedItem);
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

    public static void setResponseHeaders(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Max-Age", "86400");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setResponseHeaders(response);
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Utils.setResponseHeaders(response);
        Gson gson = new Gson();
        String queryString = request.getQueryString();

        if ( queryString != null && queryString.contains("itemId")) {

            try {
                String itemId = request.getParameter("itemId");
                Item item = ItemDatabase.getItem(itemId);
                if (item != null) {
                    Status status = new Status(200, "Success", item);
                    response.getOutputStream().print(gson.toJson(status));
                } else {
                    Status status = new Status(404, "Item Not Found", null);
                    response.getOutputStream().print(gson.toJson(status));
                }

                response.getOutputStream().flush();
            } catch (Exception ex) {
                ex.printStackTrace();
                Status status = new Status(500, ex.getMessage(), null);
                response.setStatus(500);
                response.getOutputStream().print(gson.toJson(status));
                response.getOutputStream().flush();
            }

        }

        else {
            try {
                List<Item> savedItems = ItemDatabase.getAllItems();
                Status status = new Status(200, "Success", savedItems);
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

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utils.setResponseHeaders(response);
        Gson gson = new Gson();
        String queryString = request.getQueryString();

        if ( queryString != null && queryString.contains("itemId")) {

            try {

                StringBuilder sb = new StringBuilder();
                String s;
                while ((s = request.getReader().readLine()) != null) {
                    sb.append(s);
                }

                String itemId = request.getParameter("itemId");
                Item savedItem = ItemDatabase.getItem(itemId);
                if (savedItem != null) {
                    Item newItem = (Item) gson.fromJson(sb.toString(), Item.class);
                    if (!newItem.getItemName().equals(savedItem.getItemName())) {
                        savedItem.setItemName(newItem.getItemName());
                    }
                    if (!newItem.getDescription().equals(savedItem.getDescription())) {
                        savedItem.setDescription(newItem.getDescription());
                    }
                    if (!newItem.getCategory().equals(savedItem.getCategory())) {
                        savedItem.setCategory(newItem.getCategory());
                    }

                    ItemDatabase.updateItem(savedItem);
                    Status status = new Status(200, "Success", savedItem);
                    response.getOutputStream().print(gson.toJson(status));
                } else {
                    Status status = new Status(404, "Item Not Found", null);
                    response.getOutputStream().print(gson.toJson(status));
                }

                response.getOutputStream().flush();
            } catch (Exception ex) {
                ex.printStackTrace();
                Status status = new Status(500, ex.getMessage(), null);
                response.setStatus(500);
                response.getOutputStream().print(gson.toJson(status));
                response.getOutputStream().flush();
            }

        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Utils.setResponseHeaders(response);
        Gson gson = new Gson();
        String queryString = request.getQueryString();

        if ( queryString != null && queryString.contains("itemId")) {

            try {
                String itemId = request.getParameter("itemId");
                Item savedItem = ItemDatabase.getItem(itemId);
                if (savedItem != null) {
                    ItemDatabase.deleteItem(itemId);
                    Status status = new Status(200, "Success", null);
                    response.getOutputStream().print(gson.toJson(status));
                } else {
                    Status status = new Status(404, "Item Not Found", null);
                    response.getOutputStream().print(gson.toJson(status));
                }
                response.getOutputStream().flush();
            } catch (Exception ex) {
                ex.printStackTrace();
                Status status = new Status(500, ex.getMessage(), null);
                response.setStatus(500);
                response.getOutputStream().print(gson.toJson(status));
                response.getOutputStream().flush();
            }

        } else {
            Status status = new Status(404, "Failed", null);
            response.setStatus(404);
            response.getOutputStream().print(gson.toJson(status));
            response.getOutputStream().flush();

        }
    }
}
