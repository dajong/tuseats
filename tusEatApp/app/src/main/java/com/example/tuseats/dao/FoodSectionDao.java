package com.example.tuseats.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tuseats.model.FoodSection;

import java.util.List;

@Dao
public interface FoodSectionDao {
    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(FoodSection foodSection);

    @Query("DELETE FROM food_section_table")
    void deleteAll();

    @Query("SELECT * FROM food_section_table ORDER BY food_section_name ASC")
    LiveData<List<FoodSection>> getFoodSections();
}
