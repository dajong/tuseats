package com.example.tuseats.ViewHolder;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tuseats.utils.DataStore;
import com.example.tuseats.R;
import com.example.tuseats.model.CartItem;
import com.example.tuseats.model.Food;
import com.example.tuseats.utils.ClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView foodName;
    private TextView foodDescription;
    private TextView foodPrice;
    private TextView quantityTextView;

    private Button btn_add_to_cart;

    private Food food;
    private ClickListener clickListener;
    private EditText quantity;

    private FoodViewHolder(View itemView, ClickListener clickListener) {
        super(itemView);
        // Initialize the views.
        foodName = itemView.findViewById(R.id.foodName);
        foodDescription = itemView.findViewById(R.id.foodDescription);
        foodPrice = itemView.findViewById(R.id.foodPrice);
        quantityTextView = itemView.findViewById(R.id.quantity_textview);

        // buttons
        btn_add_to_cart = itemView.findViewById(R.id.btn_add_to_cart);

        // Set the OnClickListener to the view.
        btn_add_to_cart.setOnClickListener(this);

        this.clickListener = clickListener;
        quantity = itemView.findViewById(R.id.quantity);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            quantityTextView.setVisibility(View.INVISIBLE);
            quantity.setVisibility(View.INVISIBLE);
            btn_add_to_cart.setVisibility(View.INVISIBLE);
        }
    }

    public void bind(Food food) {
        foodName.setText(food.getName());
        foodDescription.setText(food.getDescription());
        foodPrice.setText("€" + food.getPrice());
        this.food = food;
    }

    public static FoodViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list_item, parent, false);
        return new FoodViewHolder(view, new ClickListener() {
            @Override
            public void addToCart(int p, CartItem cartItem) {
                DataStore ds = DataStore.getCart();
                boolean itemAdded = false;
                for (CartItem cartitem : ds.cart) {
                    if (cartitem.getFood().getName().equals(cartItem.getFood().getName())) {
                        cartitem.setQuantity(cartitem.getQuantity() + cartItem.getQuantity());
                        itemAdded = true;
                    }
                }
                if (itemAdded == false) {
                    ds.cart.add(cartItem);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_to_cart:
                String quantityStr = quantity.getText().toString().trim();
                if (TextUtils.isEmpty(quantityStr) || quantityStr.equals("0")) {
                    quantity.setError("Please enter a valid value!");
                } else {
                    CartItem cartItem = new CartItem(this.food, Integer.parseInt(quantityStr));
                    clickListener.addToCart(this.getLayoutPosition(), cartItem);

                    // A toast to confirm item added to cart
                    Toast.makeText(view.getContext(), "Item added to carts", Toast.LENGTH_SHORT).show();

                    InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(view.getContext().INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);

                    quantity.setText("0");
                }
                break;
            default:
                break;
        }
    }
}
