package com.example.tuseats;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tuseats.model.CartItem;

public class Checkout extends AppCompatActivity {
    private Spinner month;
    private Spinner year;
    private TextView totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        month = findViewById(R.id.month_spinner);
        year = findViewById(R.id.year_spinner);
        totalPrice = findViewById(R.id.checkout_total_price);

        String[] months = new String[]{"MM", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        String[] years = new String[]{"YY", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33"};

        ArrayAdapter<String> adapter_month = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, months);
        month.setAdapter(adapter_month);

        ArrayAdapter<String> adapter_year = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        year.setAdapter(adapter_year);

        Double totPrice = 0.0;
        for (CartItem item : DataStore.getCart().cart) {
            totPrice += item.getFood().getPrice() * item.getQuantity();
        }

        totalPrice.setText("Total Price: €" + totPrice);
    }
}