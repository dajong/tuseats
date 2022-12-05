package com.example.tuseats;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
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
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionBar.setTitle("TUSeats");
        getSupportActionBar().setIcon(R.drawable.ic_baseline_food_bank_24);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.food_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_home:
                Intent intent_home = new Intent(FoodList.this, MainActivity.class);
                startActivity(intent_home);
                return true;

            case R.id.menu_cart:
                Intent intent_cart = new Intent(FoodList.this, Cart.class);
                startActivity(intent_cart);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}