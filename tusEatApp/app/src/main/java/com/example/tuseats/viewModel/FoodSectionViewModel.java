package com.example.tuseats.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tuseats.model.FoodSection;
import com.example.tuseats.repository.FoodSectionRepository;

import java.util.List;

public class FoodSectionViewModel extends AndroidViewModel {
    private FoodSectionRepository mRepository;

    private final LiveData<List<FoodSection>> mAllFoodSections;

    public FoodSectionViewModel(Application application) {
        super(application);
        mRepository = new FoodSectionRepository(application);
        mAllFoodSections = mRepository.getAllFoodSections();
    }

    public LiveData<List<FoodSection>> getAllFoodSections() {
        return mAllFoodSections;
    }

    public void insert(FoodSection foodSection) {
        mRepository.insert(foodSection);
    }
}
