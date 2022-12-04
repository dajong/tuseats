package com.example.tuseats;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuseats.Adapter.OrderListAdapter;
import com.example.tuseats.viewModel.OrderViewModel;

public class OrderHistoryList extends AppCompatActivity {
    private OrderViewModel mOrderViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_list);

        RecyclerView recyclerView = findViewById(R.id.orderListRecyclerView);
        final OrderListAdapter adapter = new OrderListAdapter(new OrderListAdapter.OrderDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mOrderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        mOrderViewModel.getAllOrders().observe(this, order -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(order);
        });
    }
}