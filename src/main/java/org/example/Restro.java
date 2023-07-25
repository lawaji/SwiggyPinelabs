package org.example;

import java.util.List;

public class Restro {
    private String restro_id;
    private String restro_name;
    Location loc;
    List<Dish> menu;

    public void setRestro_id(String restro_id) {
        this.restro_id = restro_id;
    }

    public void setRestro_name(String restro_name) {
        this.restro_name = restro_name;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public void setMenu(List<Dish> menu) {
        this.menu = menu;
    }

    public String getRestro_id() {
        return restro_id;
    }

    public String getRestro_name() {
        return restro_name;
    }

    public Location getLoc() {
        return loc;
    }

    public List<Dish> getMenu() {
        return menu;
    }

    @Override
    public String toString() {
        return "Restro{" +
                "restro_id='" + restro_id + '\'' +
                ", restro_name='" + restro_name + '\'' +
                ", loc=" + loc +
                ", menu=" + menu +
                '}';
    }
}
