package com.example.tuseats.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.tuseats.dao.FoodSectionDao;
import com.example.tuseats.dao.mRoomDatabase;
import com.example.tuseats.model.FoodSection;

import java.util.List;

public class FoodSectionRepository {
    private FoodSectionDao mFoodSectionDao;
    private LiveData<List<FoodSection>> mAllFoodSections;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public FoodSectionRepository(Application application) {
        mRoomDatabase db = mRoomDatabase.getDatabase(application);
        mFoodSectionDao = db.foodSectionDao();
        mAllFoodSections = mFoodSectionDao.getFoodSections();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<FoodSection>> getAllFoodSections() {
        return mAllFoodSections;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(FoodSection foodSection) {
        mRoomDatabase.databaseWriteExecutor.execute(() -> {
            mFoodSectionDao.insert(foodSection);
        });
    }
}
