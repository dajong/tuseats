package com.example.tuseats.model;

import java.util.Map;

public class Order {
    public Order(String dateOrdered, String userOrdered, Double priceOrdered, Map<String, Integer> foodOrdered, String orderNotes, boolean foodReady) {
        this.dateOrdered = dateOrdered;
        this.userOrdered = userOrdered;
        this.priceOrdered = priceOrdered;
        this.foodOrdered = foodOrdered;
        this.orderNotes = orderNotes;
        this.foodReady = foodReady;
    }

    public Order() {
    }

    public String getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(String dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public String getUserOrdered() {
        return userOrdered;
    }

    public void setUserOrdered(String userOrdered) {
        this.userOrdered = userOrdered;
    }

    public Double getPriceOrdered() {
        return priceOrdered;
    }

    public void setPriceOrdered(Double priceOrdered) {
        this.priceOrdered = priceOrdered;
    }

    public Map<String, Integer> getFoodOrdered() {
        return foodOrdered;
    }

    public void setFoodOrdered(Map<String, Integer> foodOrdered) {
        this.foodOrdered = foodOrdered;
    }

    public String getOrderNotes() {
        return orderNotes;
    }

    public void setOrderNotes(String orderNotes) {
        this.orderNotes = orderNotes;
    }

    public boolean isFoodReady() {
        return foodReady;
    }

    public void setFoodReady(boolean foodReady) {
        this.foodReady = foodReady;
    }

    private String dateOrdered;

    private String userOrdered;

    private Double priceOrdered;

    private Map<String, Integer> foodOrdered;

    private String orderNotes;

    private boolean foodReady;
}
