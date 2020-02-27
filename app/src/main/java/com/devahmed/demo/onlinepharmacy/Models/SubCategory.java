package com.devahmed.demo.onlinepharmacy.Models;

import java.util.List;

public class SubCategory {
    private String id;
    private String title;
    private String image;
    private String category;
    private boolean inOffer;

    public String getCategory() {
        return category;
    }

    public boolean isInOffer() {
        return inOffer;
    }

    public void setInOffer(boolean inOffer) {
        this.inOffer = inOffer;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public SubCategory() {
    }

    public SubCategory(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
