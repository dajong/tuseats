package com.example.tuseats.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.tuseats.dao.FoodDao;
import com.example.tuseats.dao.mRoomDatabase;
import com.example.tuseats.model.Food;

import java.util.List;

public class FoodRepository {
    private FoodDao mFoodDao;
    private LiveData<List<Food>> mAllFoods;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public FoodRepository(Application application) {
        mRoomDatabase db = mRoomDatabase.getDatabase(application);
        mFoodDao = db.foodDao();
        mAllFoods = mFoodDao.getAlphabetisedFoodByName();
    }


    public LiveData<List<Food>> getAllFoods() {
        return mAllFoods;
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Food>> getAllFoodsBySection(String foodSection) {
        return mFoodDao.getAlphabetisedFoodByName(foodSection);
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Food food) {
        mRoomDatabase.databaseWriteExecutor.execute(() -> {
            mFoodDao.insert(food);
        });
    }

}
