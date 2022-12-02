package com.example.tuseats.dao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tuseats.model.Food;
import com.example.tuseats.model.FoodSection;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Food.class, FoodSection.class}, version = 1, exportSchema = false)
public abstract class mRoomDatabase extends RoomDatabase {
    public abstract FoodDao foodDao();

    public abstract FoodSectionDao foodSectionDao();

    private static volatile mRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static mRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (mRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    mRoomDatabase.class, "mDatabase").addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                FoodDao dao = INSTANCE.foodDao();
                FoodSectionDao foodSectionDao = INSTANCE.foodSectionDao();

                foodSectionDao.deleteAll();
                dao.deleteAll();


                //Food
                Food food = new Food("Special Curry", 6.99, "Best curry in Limerick! Cooked with various spices, vegetables, prawns, chicken and beef", "Main Course");
                dao.insert(food);
                food = new Food("Poo", 102.50, "Poo I made this morning in my toilet", "Main Course");
                dao.insert(food);

                int count = 0;
                // Food Section
                FoodSection foodSection = new FoodSection("Main Course", "Cheap, fulfilling and most importantly, tasty!", 1);
                foodSectionDao.insert(foodSection);

                foodSection = new FoodSection("Side", "Some of the best you want with your main!", 2);
                foodSectionDao.insert(foodSection);

            });
        }
    };

}


