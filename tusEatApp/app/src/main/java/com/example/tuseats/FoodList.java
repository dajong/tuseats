package com.example.tuseats;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuseats.Adapter.FoodListAdapter;
import com.example.tuseats.viewModel.FoodViewModel;

public class FoodList extends AppCompatActivity {
    private FoodViewModel mFoodViewModel;
    private TextView foodSectionTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //Setting the food section title
        foodSectionTitle = findViewById(R.id.foodSectionTitle);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        foodSectionTitle.setText(title);

        RecyclerView recyclerView = findViewById(R.id.foodListRecyclerView);
        final FoodListAdapter adapter = new FoodListAdapter(new FoodListAdapter.FoodDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFoodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        mFoodViewModel.getAllFoodsByFoodSection(title).observe(this, foods -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(foods);
        });
        
    }
}