package com.example.tuseats.utils;

import com.example.tuseats.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static DataStore dataStore = null;

    public List<CartItem> cart;
    public boolean welcome_notify;

    private DataStore() {
        cart = new ArrayList<>();
        welcome_notify = false;
    }

    public static DataStore getCart() {
        if (dataStore == null) {
            dataStore = new DataStore();
        }

        return dataStore;
    }

    public static void setCart(DataStore ds) {
        if (dataStore == null) {
            dataStore = new DataStore();
        }
        dataStore = ds;
    }


}
