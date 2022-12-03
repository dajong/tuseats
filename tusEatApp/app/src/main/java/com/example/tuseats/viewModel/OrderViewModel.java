package com.example.tuseats.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tuseats.model.Order;
import com.example.tuseats.repository.OrderRepository;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {
    private OrderRepository mRepository;

    private final LiveData<List<Order>> mAllOrders;

    public OrderViewModel(Application application) {
        super(application);
        mRepository = new OrderRepository(application);
        mAllOrders = mRepository.getAllOrders();
    }

    public LiveData<List<Order>> getAllOrders() {
        return mRepository.getAllOrders();
    }

    public void insert(Order order) {
        mRepository.insert(order);
    }

}