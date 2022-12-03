package com.example.tuseats;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuseats.Adapter.CartListAdapter;

public class Cart extends AppCompatActivity {
    private Button checkout_button;
    private TextView cart_empty_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        checkout_button = findViewById(R.id.checkout_button);
        cart_empty_text = findViewById(R.id.cart_empty_textview);

        RecyclerView recyclerView = findViewById(R.id.cart_recycler_view);
        final CartListAdapter adapter = new CartListAdapter(new CartListAdapter.CartDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.submitList(DataStore.getCart().cart);

        if (!DataStore.getCart().cart.isEmpty()) {
            checkout_button.setVisibility(View.VISIBLE);
            cart_empty_text.setVisibility(View.INVISIBLE);
        }
    }

    public void checkout(View view) {
        Intent intent = new Intent(this, Checkout.class);
        startActivity(intent);
    }
}