package com.example.tuseats.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "food_section_table")
public class FoodSection {
    public FoodSection(@NonNull String foodSectionName, @NonNull String shortDescription, @NonNull int imageID) {
        this.foodSectionName = foodSectionName;
        this.shortDescription = shortDescription;
        this.imageID = imageID;
    }

    @NonNull
    public String getFoodSectionName() {
        return foodSectionName;
    }

    public void setFoodSectionName(@NonNull String foodSectionName) {
        this.foodSectionName = foodSectionName;
    }

    @NonNull
    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(@NonNull String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @NonNull
    public int getImageID() {
        return imageID;
    }

    public void setImageID(@NonNull int imageID) {
        this.imageID = imageID;
    }


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "food_section_name")
    private String foodSectionName;

    @NonNull
    @ColumnInfo(name = "food_section_description")
    private String shortDescription;

    @NonNull
    @ColumnInfo(name = "food_section_imageID")
    private int imageID;
}
