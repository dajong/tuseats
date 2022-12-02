package com.example.tuseats.model;

public class CartItem {
    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public CartItem(Food food, Integer quantity) {
        this.food = food;
        this.quantity = quantity;
    }

    private Food food;
    private Integer quantity;
}
