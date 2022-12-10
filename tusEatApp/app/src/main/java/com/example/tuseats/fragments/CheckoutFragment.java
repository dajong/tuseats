package com.example.tuseats.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tuseats.R;
import com.example.tuseats.activity.MainActivity;
import com.example.tuseats.model.CartItem;
import com.example.tuseats.model.Food;
import com.example.tuseats.model.Order;
import com.example.tuseats.utils.DataStore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckoutFragment extends Fragment {
    private Spinner month;
    private Spinner year;
    private TextView totalPrice;
    private EditText card_number;
    private EditText card_name;
    private EditText card_cvc;
    private EditText card_address;
    private Spinner pickupTime;
    private EditText order_notes;
    private Button button_complete_payment;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CheckoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckoutFragment newInstance(String param1, String param2) {
        CheckoutFragment fragment = new CheckoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

        month = view.findViewById(R.id.month_spinner);
        year = view.findViewById(R.id.year_spinner);
        totalPrice = view.findViewById(R.id.checkout_total_price);
        card_number = view.findViewById(R.id.card_number);
        card_name = view.findViewById(R.id.card_name);
        card_cvc = view.findViewById(R.id.card_cvc);
        card_address = view.findViewById(R.id.card_address);
        pickupTime = view.findViewById(R.id.pickupTime);
        order_notes = view.findViewById(R.id.order_notes);
        button_complete_payment = view.findViewById(R.id.complete_payment_button);

        String[] pickupTimeOption = new String[]{"Pickup Time", "12:00 pm", "12:30 pm", "13:00 pm"};
        String[] months = new String[]{"MM", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        String[] years = new String[]{"YY", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33"};

        ArrayAdapter<String> adapter_month = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, months);
        month.setAdapter(adapter_month);

        ArrayAdapter<String> adapter_year = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, years);
        year.setAdapter(adapter_year);

        ArrayAdapter<String> adapter_pickup_time = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, pickupTimeOption);
        pickupTime.setAdapter(adapter_pickup_time);
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

        button_complete_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                completePayment();
            }
        });

        return view;
    }

    public void completePayment() {
        Double totalPriceOrdered = 0.0;
        List<Food> foodOrdered = new ArrayList<>();
        Intent intent = new Intent(getContext(), MainActivity.class);
        if (pickupTime == null || pickupTime.getSelectedItem().toString() == "Pickup Time") {
            Context context = getContext();
            Toast.makeText(context, "Please pick a time to pick up your food! ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(card_number.getText())) {
            card_number.setError("Card Number is required!");
        } else if (TextUtils.isEmpty(card_name.getText())) {
            card_name.setError("Name is required!");
        } else if (month == null || month.getSelectedItem().toString() == "MM") {
            // A toast for error
            Context context = getContext();
            Toast.makeText(context, "Expire month is required", Toast.LENGTH_SHORT).show();
        } else if (year == null || year.getSelectedItem().toString() == "YY") {
            Context context = getContext();
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