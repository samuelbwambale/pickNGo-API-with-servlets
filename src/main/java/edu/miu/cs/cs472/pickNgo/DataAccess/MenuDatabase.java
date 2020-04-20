package edu.miu.cs.cs472.pickNgo.DataAccess;

import edu.miu.cs.cs472.pickNgo.model.Menu;
import edu.miu.cs.cs472.pickNgo.model.Order;
import edu.miu.cs.cs472.pickNgo.utils.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuDatabase {
    static HashMap<String, Menu> menus = new HashMap<String, Menu>();
    static LocalDate date = LocalDate.now();

    public static Menu createMenu(Menu menu){
        String menuId = Utils.generateRandomString(6);
        menu.setMenuId(menuId);
        menu.setDate(date);
//        order.addItem();
        menus.put(menuId, menu);
        return menu;
    }

    public static List<Menu> getAllMenus() {
        List<Menu> allMenus = new ArrayList<Menu>();
        for(Map.Entry<String,Menu> entry: menus.entrySet())
        {
            allMenus.add(entry.getValue());
        }
        return allMenus;
    }

    public static Menu getMenu(String menuId) {
        return menus.get(menuId);
    }

    public static void deleteMenu(String menuId) {
        menus.remove(menuId);
    }

    public static void updateMenu(Menu menu) {
        menus.put(menu.getMenuId(), menu);

    }
}

