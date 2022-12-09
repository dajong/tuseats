package com.example.tuseats;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tuseats.model.CartItem;
import com.example.tuseats.model.Food;
import com.example.tuseats.model.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Checkout extends AppCompatActivity {
    private Spinner month;
    private Spinner year;
    private TextView totalPrice;
    private EditText card_number;
    private EditText card_name;
    private EditText card_cvc;
    private EditText card_address;
    private Spinner pickupTime;
    private EditText order_notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        month = findViewById(R.id.month_spinner);
        year = findViewById(R.id.year_spinner);
        totalPrice = findViewById(R.id.checkout_total_price);
        card_number = findViewById(R.id.card_number);
        card_name = findViewById(R.id.card_name);
        card_cvc = findViewById(R.id.card_cvc);
        card_address = findViewById(R.id.card_address);
        pickupTime = findViewById(R.id.pickupTime);
        order_notes = findViewById(R.id.order_notes);

        String[] pickupTimeOption = new String[]{"Pickup Time", "12:00 pm", "12:30 pm", "13:00 pm"};
        String[] months = new String[]{"MM", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        String[] years = new String[]{"YY", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33"};

        ArrayAdapter<String> adapter_month = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, months);
        month.setAdapter(adapter_month);

        ArrayAdapter<String> adapter_year = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);
        year.setAdapter(adapter_year);

        ArrayAdapter<String> adapter_pickup_time = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pickupTimeOption);
        pickupTime.setAdapter(adapter_pickup_time);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionBar.setTitle("TUSeats");
        getSupportActionBar().setIcon(R.drawable.ic_baseline_food_bank_24);

        // Credit card number formatting
        // Code source: https://stackoverflow.com/questions/11790102/format-credit-card-in-edit-text-in-android

        card_number.addTextChangedListener(new TextWatcher() {

            private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
            private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
            private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
            private static final char DIVIDER = '-';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // nothing happened
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // nothing happened
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }
            }

            private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
                boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
                for (int i = 0; i < s.length(); i++) { // check that every element is right
                    if (i > 0 && (i + 1) % dividerModulo == 0) {
                        isCorrect &= divider == s.charAt(i);
                    } else {
                        isCorrect &= Character.isDigit(s.charAt(i));
                    }
                }
                return isCorrect;
            }

            private String buildCorrectString(char[] digits, int dividerPosition, char divider) {
                final StringBuilder formatted = new StringBuilder();

                for (int i = 0; i < digits.length; i++) {
                    if (digits[i] != 0) {
                        formatted.append(digits[i]);
                        if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                            formatted.append(divider);
                        }
                    }
                }

                return formatted.toString();
            }

            private char[] getDigitArray(final Editable s, final int size) {
                char[] digits = new char[size];
                int index = 0;
                for (int i = 0; i < s.length() && index < size; i++) {
                    char current = s.charAt(i);
                    if (Character.isDigit(current)) {
                        digits[index] = current;
                        index++;
                    }
                }
                return digits;
            }
        });
        Double totPrice = 0.0;
        for (CartItem item : DataStore.getCart().cart) {
            totPrice += item.getFood().getPrice() * item.getQuantity();
        }

        totalPrice.setText("Total Price: €" + totPrice);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_home:
                Intent intent_home = new Intent(Checkout.this, MainActivity.class);
                startActivity(intent_home);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void completePayment(View view) {
        Double totalPriceOrdered = 0.0;
        List<Food> foodOrdered = new ArrayList<>();
        Intent intent = new Intent(Checkout.this, MainActivity.class);
        if (pickupTime == null || pickupTime.getSelectedItem().toString() == "Pickup Time") {
            Context context = getApplicationContext();
            Toast.makeText(context, "Please pick a time to pick up your food! ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(card_number.getText())) {
            card_number.setError("Card Number is required!");
        } else if (TextUtils.isEmpty(card_name.getText())) {
            card_name.setError("Name is required!");
        } else if (month == null || month.getSelectedItem().toString() == "MM") {
            // A toast for error
            Context context = getApplicationContext();
            Toast.makeText(context, "Expire month is required", Toast.LENGTH_SHORT).show();
        } else if (year == null || year.getSelectedItem().toString() == "YY") {
            Context context = getApplicationContext();
            Toast.makeText(context, "Expire year is required!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(card_cvc.getText())) {
            card_cvc.setError("CVC is required!");
        } else if (TextUtils.isEmpty(card_address.getText())) {
            card_address.setError("Address is required!");
        } else {
            for (CartItem item : DataStore.getCart().cart) {
                totalPriceOrdered += item.getFood().getPrice() * item.getQuantity();
                foodOrdered.add(item.getFood());
            }
            DataStore.getCart().cart.clear();
            Date c = Calendar.getInstance().getTime();


            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String formattedDate = df.format(c);

            Order newOrder = new Order(0, formattedDate, totalPriceOrdered, foodOrdered);

            intent.putExtra("order", newOrder);
            startActivity(intent);
        }

    }
}