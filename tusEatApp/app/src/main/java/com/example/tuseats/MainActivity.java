package com.example.tuseats;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuseats.Adapter.FoodSectionListAdapter;
import com.example.tuseats.viewModel.FoodSectionViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FoodSectionViewModel mFoodSectionViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    ArrayList<Integer> imageIDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        imageIDs = fetchDrawables();
        final FoodSectionListAdapter adapter = new FoodSectionListAdapter(new FoodSectionListAdapter.FoodSectionDiff(), imageIDs);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFoodSectionViewModel = new ViewModelProvider(this).get(FoodSectionViewModel.class);
        mFoodSectionViewModel.getAllFoodSections().observe(this, foodSections -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(foodSections);
        });

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_food_bank_24);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
//        int nightMode = AppCompatDelegate.getDefaultNightMode();
//        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
//            menu.findItem(R.id.menu_night_mode).setTitle(R.string.day_mode);
//            menu.findItem(R.id.menu_night_mode).setIcon(R.drawable.ic_light_mode);
//            menu.findItem(R.id.menu_new_game).setIcon(R.drawable.ic_new_game_dark_mode);
//        } else {
//            menu.findItem(R.id.menu_night_mode).setIcon(R.drawable.ic_night_mode);
//            menu.findItem(R.id.menu_new_game).setIcon(R.drawable.ic_new_game_light_mode);
//            menu.findItem(R.id.menu_night_mode).setTitle(R.string.night_mode);
//        }
        //game_mode = menu.findItem(R.id.menu_single_mode);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_info:
                Intent intent = new Intent(MainActivity.this, Info.class);
                startActivity(intent);
                return true;

            case R.id.menu_cart:
                Intent intent_cart = new Intent(MainActivity.this, Cart.class);
                startActivity(intent_cart);
                return true;
//            case R.id.menu_night_mode:
//                int nightMode = AppCompatDelegate.getDefaultNightMode();
//                if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
//                    AppCompatDelegate.setDefaultNightMode
//                            (AppCompatDelegate.MODE_NIGHT_NO);
//                } else {
//                    AppCompatDelegate.setDefaultNightMode
//                            (AppCompatDelegate.MODE_NIGHT_YES);
//                }
//                // Recreate the activity for the theme change to take effect.
//                recreate();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public ArrayList<Integer> fetchDrawables() {
        ArrayList<Integer> list = new ArrayList<>();

        TypedArray foodSectionsImageResources =
                getResources().obtainTypedArray(R.array.food_section_images);

        for (int i = 0; i < 3; i++) {
            list.add(foodSectionsImageResources.getResourceId(i, 0));
        }

        return list;
    }


}