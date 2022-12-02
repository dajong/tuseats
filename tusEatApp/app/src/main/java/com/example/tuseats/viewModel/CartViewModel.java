package com.example.tuseats.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tuseats.DataStore;
import com.example.tuseats.model.CartItem;

import java.util.List;

public class CartViewModel extends AndroidViewModel {
    private LiveData<List<CartItem>> mAllCartItems;

    public CartViewModel(Application application) {
        super(application);
        DataStore ds = DataStore.getCart();
        mAllCartItems = (LiveData<List<CartItem>>) ds.cart;
    }

    public LiveData<List<CartItem>> getCartItems() {
        return mAllCartItems;
    }
}
