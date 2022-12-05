package com.example.tuseats.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "order_table")
public class Order implements Serializable {
    public Order(@NonNull Integer orderId, @NonNull String dateOrdered, @NonNull Double totalPrice, @NonNull List<Food> foodOrdered) {
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
    public String getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(@NonNull String dateOrdered) {
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
    private String dateOrdered;

    @NonNull
    @ColumnInfo(name = "order_price")
    private Double totalPrice;

    @TypeConverters(DataConverter.class)
    @NonNull
    @ColumnInfo(name = "order_food")
    private List<Food> foodOrdered;

}
