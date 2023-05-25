package com.project.canteenmanagementsystem.Models;

public class AddProducts {

    public String id;
    public String name;
    public String price;
    public String food_category;
    public  String img_url;

    public AddProducts() {}

    public AddProducts(String id, String name, String price, String food_category, String img_url){
        this.id = id;
        this.name = name;
        this.price = price;
        this.img_url = img_url;
        this.food_category = food_category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFood_category() {
        return food_category;
    }

    public void setFood_category(String food_category) {
        this.food_category = food_category;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
