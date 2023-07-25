package org.example;

public class Dish {
    private String dish_id;
    private String dish_name;
    private int price;

    private String restro_id;

    public void setDish_id(String dish_id) {
        this.dish_id = dish_id;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public void setRestro_id(String restro_id) {
        this.restro_id = restro_id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDish_id() {
        return dish_id;
    }

    public String getDish_name() {
        return dish_name;
    }

    public String getRestro_id() {
        return restro_id;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "dish_id='" + dish_id + '\'' +
                ", dish_name='" + dish_name + '\'' +
                ", price=" + price +
                ", restro_id='" + restro_id + '\'' +
                '}';
    }

}
