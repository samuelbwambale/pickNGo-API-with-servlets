package edu.miu.cs.cs472.pickNgo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Menu {
    private String menuId;
    private LocalDate date;
    private String category;
    private List<Item> items;

    public Menu(String menuId, String category) {
        this.menuId = menuId;
        this.date = LocalDate.now();
        this.category = category;
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void removeItem(Item item) {
        this.items.remove((Item) item);
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Menu)) return false;
        Menu menu = (Menu) o;
        return Objects.equals(getMenuId(), menu.getMenuId()) &&
                Objects.equals(getDate(), menu.getDate()) &&
                Objects.equals(getCategory(), menu.getCategory()) &&
                Objects.equals(getItems(), menu.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMenuId(), getDate(), getCategory(), getItems());
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}

