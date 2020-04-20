package edu.miu.cs.cs472.pickNgo.DataAccess;

import edu.miu.cs.cs472.pickNgo.model.Item;
import edu.miu.cs.cs472.pickNgo.utils.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemDatabase {

    static HashMap<String, Item> items = new HashMap<>();

    public static Item createItem(Item item) {
        String itemId = Utils.generateRandomString(6);
        item.setItemId(itemId);
        items.put(itemId, item);
        return item;
    }

    public static List<Item> getAllItems() {
        List<Item> allItems = new ArrayList<Item>();
        for(Map.Entry<String,Item> entry: items.entrySet())
        {
            allItems.add(entry.getValue());
        }
        return allItems;
    }

    public static Item getItem(String itemId) {
        return items.get(itemId);
    }

    public static void deleteItem(String itemId) {
        items.remove(itemId);
    }

    public static void updateItem(Item item) {
        items.put(item.getItemId(), item);

    }


}
