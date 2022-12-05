package com.example.tuseats.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "food_table")
public class Food implements Serializable {
    public Food(@NonNull String name, @NonNull Double price, @NonNull String description, @NonNull String foodSection) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.foodSection = foodSection;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public Double getPrice() {
        return price;
    }

    public void setPrice(@NonNull Double price) {
        this.price = price;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    @NonNull
    public String getFoodSection() {
        return foodSection;
    }

    public void setFoodSection(@NonNull String foodSection) {
        this.foodSection = foodSection;
    }

    // We can add a auto generate arguments if we wish to do so
    // @PrimaryKey(autoGenerate = true)
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "food_name")
    private String name;

    @NonNull
    @ColumnInfo(name = "food_price")
    private Double price;

    @NonNull
    @ColumnInfo(name = "food_description")
    private String description;

    @NonNull
    @ColumnInfo(name = "food_section")
    private String foodSection;

}
