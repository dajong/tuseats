package com.example.tuseats.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tuseats.model.Order;

import java.util.List;

@Dao
public interface OrderDao {
    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Order order);

    @Query("DELETE FROM order_table")
    void deleteAll();

    @Query("SELECT * FROM order_table ORDER BY order_date DESC")
    LiveData<List<Order>> getAllOrders();
}
