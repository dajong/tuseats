package com.example.tuseats.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuseats.Adapter.CartListAdapter;
import com.example.tuseats.R;
import com.example.tuseats.utils.DataStore;
import com.google.gson.Gson;

public class Cart extends AppCompatActivity {
    private static Button checkout_button;
    private static TextView cart_empty_text;
    SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mPrefs = getSharedPreferences("cart_values", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("cart", "");
        if (!json.equals("") && DataStore.getCart().cart.isEmpty()) {
            DataStore.setCart(gson.fromJson(json, DataStore.class));
        }

        checkout_button = findViewById(R.id.checkout_button);
        cart_empty_text = findViewById(R.id.cart_empty_textview);

        RecyclerView recyclerView = findViewById(R.id.cart_recycler_view);
        final CartListAdapter adapter = new CartListAdapter(new CartListAdapter.CartDiff(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.submitList(DataStore.getCart().cart);

        if (!DataStore.getCart().cart.isEmpty()) {
            checkout_button.setVisibility(View.VISIBLE);
            cart_empty_text.setVisibility(View.INVISIBLE);
        }

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionBar.setTitle("TUSeats");
        getSupportActionBar().setIcon(R.drawable.ic_baseline_food_bank_24);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_home:
                Intent intent_home = new Intent(Cart.this, MainActivity.class);
                startActivity(intent_home);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void checkout(View view) {
        Intent intent = new Intent(this, Checkout.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(DataStore.getCart());
        prefsEditor.putString("cart", json);
        prefsEditor.commit();
    }

    public static void setCart_empty_text() {
        checkout_button.setVisibility(View.INVISIBLE);
        cart_empty_text.setVisibility(View.VISIBLE);
    }
}