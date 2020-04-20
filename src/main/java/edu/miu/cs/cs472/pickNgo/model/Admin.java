package edu.miu.cs.cs472.pickNgo.model;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User {

    public Admin(String id, String name, String password, String type) {
        super(id, name, password, type);
    }

    public List<Item> createMenu (List<Item> selectedItems) {
        ArrayList<Item> menu = new ArrayList<>();
        for(Item e : selectedItems){
            if(!menu.contains((Item) e)){
                menu.add(e);
            }
        }
        return menu;
    }
}
