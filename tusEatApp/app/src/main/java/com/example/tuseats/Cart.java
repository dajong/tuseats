package com.example.tuseats;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuseats.Adapter.CartListAdapter;
import com.example.tuseats.viewModel.CartViewModel;

public class Cart extends AppCompatActivity {
    private CartViewModel mCartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        RecyclerView recyclerView = findViewById(R.id.cart_recycler_view);
        final CartListAdapter adapter = new CartListAdapter(new CartListAdapter.CartDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.submitList(DataStore.getCart().cart);
    }
}