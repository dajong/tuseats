package com.example.tuseats.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.tuseats.dao.OrderDao;
import com.example.tuseats.dao.mRoomDatabase;
import com.example.tuseats.model.Order;

import java.util.List;

public class OrderRepository {
    private OrderDao mOrderDao;
    private LiveData<List<Order>> mAllOrders;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public OrderRepository(Application application) {
        mRoomDatabase db = mRoomDatabase.getDatabase(application);
        mOrderDao = db.orderDao();
        mAllOrders = mOrderDao.getAllOrders();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Order>> getAllOrders() {
        return mAllOrders;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Order order) {
        mRoomDatabase.databaseWriteExecutor.execute(() -> {
            mOrderDao.insert(order);
        });
    }
}
