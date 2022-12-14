package com.example.tuseats.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuseats.Adapter.FoodSectionListAdapter;
import com.example.tuseats.R;
import com.example.tuseats.utils.DataStore;
import com.example.tuseats.viewModel.FoodSectionViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotifyManager;

    private FoodSectionViewModel mFoodSectionViewModel;
    private FloatingActionButton button_cart;
    ArrayList<Integer> imageIDs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create the notification channel.
        createNotificationChannel();

        // Send a notification when user open the app
        if (!DataStore.getCart().welcome_notify) {
            sendNotification();
            DataStore.getCart().welcome_notify = true;
        }

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

        button_cart = findViewById(R.id.fab);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            button_cart.setVisibility(View.INVISIBLE);
        } else {
            button_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_cart = new Intent(MainActivity.this, Cart.class);
                    startActivity(intent_cart);
                }
            });
        }


        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionBar.setTitle("TUSeats");
        getSupportActionBar().setIcon(R.drawable.ic_baseline_food_bank_24);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_menu, menu);
        } else {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_menu_guest, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_info:
                Intent intent_info = new Intent(MainActivity.this, Info.class);
                startActivity(intent_info);
                return true;

            case R.id.menu_maps:
                Intent intent_maps = new Intent(MainActivity.this, Maps.class);
                startActivity(intent_maps);
                return true;

            case R.id.menu_history:
                Intent intent_order_history = new Intent(MainActivity.this, OrderHistoryList.class);
                startActivity(intent_order_history);
                return true;

            case R.id.menu_contact:
                Intent intent_contact_staff = new Intent(MainActivity.this, Contact.class);
                startActivity(intent_contact_staff);
                return true;

            case R.id.menu_login:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    FirebaseAuth.getInstance().signOut();
                    DataStore.getCart().cart.clear();
                    SharedPreferences mPrefs = getSharedPreferences("cart_values", MODE_PRIVATE);
                    mPrefs.edit().clear().commit();
                    finish();
                    startActivity(getIntent());
                } else {
                    Intent intent_order_login = new Intent(MainActivity.this, Login.class);
                    startActivity(intent_order_login);
                }
                return true;


            case R.id.menu_logout:
                user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    startActivity(getIntent());
                }
                return true;
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

    public void createNotificationChannel() {
        mNotifyManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            // Create a NotificationChannel
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Test Notification", NotificationManager
                    .IMPORTANCE_DEFAULT);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.CYAN);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification for testing");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    public void sendNotification() {
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

    }

    private NotificationCompat.Builder getNotificationBuilder() {
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID).setContentTitle("Welcome to TUSEats")
                .setContentText("Try out our student favorite Special Curry today!")
                .setSmallIcon(R.drawable.ic_baseline_food_bank_24);
        return notifyBuilder;
    }

    @Override
    public void onBackPressed() {
        // Do nothing to prevent user go back to payment page
        return;
    }
}