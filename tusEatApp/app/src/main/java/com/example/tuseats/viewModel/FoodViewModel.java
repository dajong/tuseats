package com.example.tuseats.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tuseats.model.Food;
import com.example.tuseats.repository.FoodRepository;

import java.util.List;

public class FoodViewModel extends AndroidViewModel {
    private FoodRepository mRepository;

    private final LiveData<List<Food>> mAllFoods;

    public FoodViewModel(Application application) {
        super(application);
        mRepository = new FoodRepository(application);
        mAllFoods = mRepository.getAllFoods();
    }

    public LiveData<List<Food>> getAllFoodsByFoodSection(String foodSection) {
        return mRepository.getAllFoodsBySection(foodSection);
    }

    public void insert(Food word) {
        mRepository.insert(word);
    }
}
