package edu.miu.cs.cs472.pickNgo.DataAccess;

import edu.miu.cs.cs472.pickNgo.model.User;
import edu.miu.cs.cs472.pickNgo.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDatabase {
    static HashMap<String, User> users = new HashMap<>();

    public static User createUser(User user) {
        String userId = Utils.generateRandomString(6);
        user.setUserId(userId);
        users.put(user.getUsername(), user);
        return user;
    }

    public static List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<User>();
        for(Map.Entry<String,User> entry: users.entrySet())  {
            allUsers.add(entry.getValue());
        }
        return allUsers;
    }

    public static User getUser(String username) {
        return users.get(username);
    }

    public static void deleteUser(String userId) {
        users.remove(userId);
    }

    public static void updateUser(User user) {
        users.put(user.getUserId(), user);

    }

}
