package com.example.tuseats.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity(tableName = "order_table")
public class Order {
    public Order(@NonNull Integer orderId, @NonNull Date dateOrdered, @NonNull Double totalPrice, @NonNull List<Food> foodOrdered) {
        this.orderId = orderId;
        this.dateOrdered = dateOrdered;
        this.totalPrice = totalPrice;
        this.foodOrdered = foodOrdered;
    }

    @NonNull
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(@NonNull Integer orderId) {
        this.orderId = orderId;
    }

    @NonNull
    public Date getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(@NonNull Date dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    @NonNull
    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(@NonNull Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @NonNull
    public List<Food> getFoodOrdered() {
        return foodOrdered;
    }

    public void setFoodOrdered(@NonNull List<Food> foodOrdered) {
        this.foodOrdered = foodOrdered;
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "order_id")
    private Integer orderId;

    @NonNull
    @ColumnInfo(name = "order_date")
    private Date dateOrdered;

    @NonNull
    @ColumnInfo(name = "order_price")
    private Double totalPrice;

    @NonNull
    @ColumnInfo(name = "order_food")
    private List<Food> foodOrdered;

}
