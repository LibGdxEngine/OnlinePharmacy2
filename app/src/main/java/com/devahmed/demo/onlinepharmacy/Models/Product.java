package com.devahmed.demo.onlinepharmacy.Models;

public class Product {

    private String id;
    private String title;
    private int price;
    private String image;
    private String category;
    private boolean isInOffer;
    private boolean isPromotioned;
    private boolean isBestSeller;

    public boolean isInOffer() {
        return isInOffer;
    }

    public void setInOffer(boolean inOffer) {
        isInOffer = inOffer;
    }

    public boolean isPromotioned() {
        return isPromotioned;
    }

    public void setPromotioned(boolean promotioned) {
        isPromotioned = promotioned;
    }

    public boolean isBestSeller() {
        return isBestSeller;
    }

    public void setBestSeller(boolean bestSeller) {
        isBestSeller = bestSeller;
    }

    public Product() {
    }

    public Product(String title, int price, String image, String category) {
        this.title = title;
        this.price = price;
        this.image = image;
        this.category = category;
        this.isInOffer = false;
        this.isBestSeller = false;
        this.isPromotioned = false;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String mTitle) {
        this.title = mTitle;
    }
}
