package com.devahmed.demo.onlinepharmacy.Models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String name;
    private String userId;
    private String phone;
    private double xPos;
    private double yPos;
    private String GpsAddress;
    private String area;
    private String street;
    private String flat;
    private String uniqueSign;
    private int points;
    private boolean isBlocked;
    private String nearestBranch;

    public User() {
        this.userId = "1";
        this.userId = "2";
        this.name = "";
        this.phone = "4";
        this.xPos = 0;
        this.yPos = 0;
        this.GpsAddress = "5";
        this.area = "6";
        this.street = "7";
        this.flat = "8";
        this.uniqueSign = "9";
        this.points = 0;
        this.isBlocked =false;
        this.nearestBranch = "";
    }

    public User(String phone, int points) {
        this.phone = phone;
        this.points = points;
    }

    public User(String name ,String phone, double xPos, double yPos, String gpsAddress, String area
            , String street, String flat, String uniqueSign, int points ,String nearestBranch, boolean isBlocked) {
        this.phone = phone;
        this.xPos = xPos;
        this.yPos = yPos;
        GpsAddress = gpsAddress;
        this.area = area;
        this.street = street;
        this.flat = flat;
        this.uniqueSign = uniqueSign;
        this.points = points;
        this.name = name;
        this.isBlocked = isBlocked;
        this.nearestBranch = nearestBranch;
    }

    public String getNearestBranch() {
        return nearestBranch;
    }

    public void setNearestBranch(String nearestBranch) {
        this.nearestBranch = nearestBranch;
    }

    public boolean getIsBlocked() {
        return this.isBlocked;
    }

    public void setIsBlocked(boolean blocked) {
        this.isBlocked = blocked;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    public String getGpsAddress() {
        return GpsAddress;
    }

    public void setGpsAddress(String gpsAddress) {
        GpsAddress = gpsAddress;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getUniqueSign() {
        return uniqueSign;
    }

    public void setUniqueSign(String uniqueSign) {
        this.uniqueSign = uniqueSign;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("isBlocked" , isBlocked);
        result.put("userId", userId);
        result.put("name" , name);
        result.put("phone", phone);
        result.put("xPos", xPos);
        result.put("yPos" , yPos);
        result.put("GpsAddress" , GpsAddress);
        result.put("area" , area);
        result.put("street" , street);
        result.put("flat" , flat);
        result.put("uniqueSign" , uniqueSign);
        result.put("points" , points);
        result.put("nearestBranch" , nearestBranch);
        return result;
    }
}
