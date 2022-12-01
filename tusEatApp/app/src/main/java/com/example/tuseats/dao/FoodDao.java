package com.example.tuseats.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tuseats.model.Food;

import java.util.List;

@Dao
public interface FoodDao {

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Food food);

    @Query("DELETE FROM food_table")
    void deleteAll();

    // Might need to change to MutableLiveData if we were to amend the object
    @Query("SELECT * FROM food_table ORDER BY food_name ASC")
    LiveData<List<Food>> getAlphabetisedFoodByName();

    // Might need to change to MutableLiveData if we were to amend the object
    @Query("SELECT * FROM food_table WHERE food_section=:foodSection ORDER BY food_name ASC")
    LiveData<List<Food>> getAlphabetisedFoodByName(String foodSection);


    // The three dots after the user object is called variable arguments in Java
    // It allow the method to accept 0 - many arguments
    // However they can only be passed in as the last argument of the method

    //    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //    public void insertUsers(Food... foods);
}
