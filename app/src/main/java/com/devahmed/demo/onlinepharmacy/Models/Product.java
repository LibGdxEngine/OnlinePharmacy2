package com.devahmed.demo.onlinepharmacy.Models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Product {

    private String id;
    private String title;
    private int price;
    private String image;
    private String subCategory;

    public Product() {
    }

    public Product(String title, int price, String image, String subCategory) {
        this.title = title;
        this.price = price;
        this.image = image;
        this.subCategory = subCategory;
    }


    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String mTitle) {
        this.title = mTitle;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("title", title);
        result.put("image", image);
        result.put("price" , price);
        result.put("subCategory" , subCategory);
        return result;
    }
}
