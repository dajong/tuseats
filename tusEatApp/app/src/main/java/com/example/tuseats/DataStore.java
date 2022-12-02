package com.example.tuseats;

import com.example.tuseats.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static DataStore dataStore = null;

    public List<CartItem> cart;

    private DataStore() {
        cart = new ArrayList<>();
    }

    public static DataStore getCart() {
        if (dataStore == null) {
            dataStore = new DataStore();
        }

        return dataStore;
    }


}
