package com.project.canteenmanagementsystem.UsersActivity.Model;

public class CartModel implements Comparable {

    public String id;
    public String name;
    public String price;
    public int quantity;
    public static double total;

    public CartModel(String id, String name, String price, int quantity, double total) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
    }

    @Override
    public String toString() {
        return "CartModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", quantity=" + quantity +
                ", total=" + total +
                '}';
    }

    @Override
    public int compareTo( Object o) {
        return   name.compareTo(((CartModel)o).name);
        // return  2;
    }
}