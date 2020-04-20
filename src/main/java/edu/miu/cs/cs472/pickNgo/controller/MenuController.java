package edu.miu.cs.cs472.pickNgo.controller;

import com.google.gson.Gson;
import edu.miu.cs.cs472.pickNgo.DataAccess.ItemDatabase;
import edu.miu.cs.cs472.pickNgo.DataAccess.MenuDatabase;
import edu.miu.cs.cs472.pickNgo.model.Item;
import edu.miu.cs.cs472.pickNgo.model.Menu;
import edu.miu.cs.cs472.pickNgo.utils.Status;
import edu.miu.cs.cs472.pickNgo.utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "MenuController", urlPatterns = {"/menus"})
public class MenuController extends HttpServlet {

    protected static void processMenuRequest(HttpServletRequest request, HttpServletResponse response)
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

            Menu menu = (Menu) gson.fromJson(sb.toString(), Menu.class);
            List<Item> selectedItems = new ArrayList<>();
            for(Item item : menu.getItems()) {
                System.out.println("item " + item.getItemId());
                String itemId = item.getItemId();

                Item savedItem = ItemDatabase.getItem(itemId);
                selectedItems.add(savedItem);
            }
            menu.setItems(selectedItems);
            Menu menuDb = MenuDatabase.createMenu(menu);
            Status status = new Status(200, "Success", menuDb);

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setResponseHeaders(response);
        processMenuRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utils.setResponseHeaders(response);
        Gson gson = new Gson();
        String queryString = request.getQueryString();
        if (queryString != null && queryString.contains("menuId")) {
            try {
                String menuId = request.getParameter("menuId");
                Menu menu = MenuDatabase.getMenu(menuId);
                Status status = new Status(200, "Success", menu);
                response.getOutputStream().print(gson.toJson(status));
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

                List<Menu> selectedMenus = MenuDatabase.getAllMenus();
                Status status = new Status(200, "Success", selectedMenus);
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

        if(queryString != null && queryString.contains("menuId")){
            try{
                StringBuilder sb = new StringBuilder();
                String s;
                List<String> list = new ArrayList<>();
                while ((s = req.getReader().readLine()) != null) {
                    sb.append(s);
                }

                Menu newMenu = (Menu) gson.fromJson(sb.toString(), Menu.class);
                String menuId = req.getParameter("menuId");
                List<Item> newSelectedItems = new ArrayList<>();
                for(Item item : newMenu.getItems()) {
                    String itemId = item.getItemId();
                    Item savedItem = ItemDatabase.getItem(itemId);
                    newSelectedItems.add(savedItem);
                }
                newMenu.setItems(newSelectedItems);
                Menu savedMenu = MenuDatabase.getMenu(menuId);

                if(!newMenu.getItems().equals(savedMenu.getItems())){
                    savedMenu.setItems(newSelectedItems);
                }

                MenuDatabase.updateMenu(savedMenu);
                Status status = new Status(200, "Success", newMenu);
                resp.getOutputStream().print(gson.toJson(status));
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
        if(queryString != null && queryString.contains("menuId")){
            try{
                String menuId = req.getParameter("menuId");
                Menu menu = MenuDatabase.getMenu(menuId);
                if(menu != null){
                    MenuDatabase.deleteMenu(menuId);
                    Status status = new Status(200, "Success", null);
                    resp.getOutputStream().print(gson.toJson(status));

                }else{
                    Status status = new Status(404, "Menu Is Not Available", null);
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
