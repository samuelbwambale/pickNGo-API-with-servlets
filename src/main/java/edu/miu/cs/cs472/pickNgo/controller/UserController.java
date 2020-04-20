package edu.miu.cs.cs472.pickNgo.controller;


import com.google.gson.Gson;

import edu.miu.cs.cs472.pickNgo.DataAccess.ItemDatabase;
import edu.miu.cs.cs472.pickNgo.DataAccess.UserDatabase;
import edu.miu.cs.cs472.pickNgo.model.Admin;
import edu.miu.cs.cs472.pickNgo.model.Customer;
import edu.miu.cs.cs472.pickNgo.model.Item;
import edu.miu.cs.cs472.pickNgo.model.User;
import edu.miu.cs.cs472.pickNgo.utils.Status;
import edu.miu.cs.cs472.pickNgo.utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserController", urlPatterns = {"/login"})
public class UserController extends HttpServlet {

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setResponseHeaders(response);
        User admin = new User("GHK","admin", "password", "admin");
        User user = new User("GHKK","user", "password", "customer");
        User u1 = UserDatabase.createUser(admin);
        User u2 = UserDatabase.createUser(user);
        login(request, response);
    }



    public  static void  signup(String username, String id, String password, String type, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (type.equals("admin")) {
            User user = new Admin(id, username, password, type);
            User savedUser = UserDatabase.getUser(user.getUsername());
            if (savedUser != null) {
                request.getRequestDispatcher("/WEB-INF/views/Login.jsp").forward(request, response);
            } else {
                User admin = UserDatabase.createUser(user);
                request.getRequestDispatcher("/WEB-INF/views/Login.jsp").forward(request, response);
            }

        }

        if (type.equals("customer")) {
            User cusUser = new Customer(id, username, password, type);
            User savedUser = UserDatabase.getUser(cusUser.getUsername());
            if (savedUser != null) {
                request.getRequestDispatcher("/WEB-INF/views/Login.jsp").forward(request, response);
            } else {
                User cus = UserDatabase.createUser(cusUser);
                request.getRequestDispatcher("/WEB-INF/views/Login.jsp").forward(request, response);
            }
        }
    }

    public static void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utils.setResponseHeaders(response);
        Gson gson = new Gson();

        try {

            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = request.getReader().readLine()) != null) {
                sb.append(s);
                System.out.println(sb);
            }

            User user = (User) gson.fromJson(sb.toString(), User.class);
            User savedUser = UserDatabase.getUser(user.getUsername());
            System.out.println(savedUser.toString());
            if(savedUser != null) {
                if (savedUser.getUsername().equals(user.getUsername()) && savedUser.getPassword().equals(user.getPassword())) {
                    Status status = new Status(200, "Success", savedUser);
                    response.getOutputStream().print(gson.toJson(status));
                }
            } else {
                Status status = new Status(404, "User Not Found", null);
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

