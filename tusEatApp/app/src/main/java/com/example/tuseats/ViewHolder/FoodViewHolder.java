package com.example.tuseats.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tuseats.DataStore;
import com.example.tuseats.R;
import com.example.tuseats.model.CartItem;
import com.example.tuseats.model.Food;
import com.example.tuseats.utils.ClickListener;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView foodName;
    private TextView foodDescription;
    private TextView foodPrice;
    private Button btn_add_to_cart;
    private Food food;
    private ClickListener clickListener;

    private FoodViewHolder(View itemView, ClickListener clickListener) {
        super(itemView);
        // Initialize the views.
        foodName = itemView.findViewById(R.id.foodName);
        foodDescription = itemView.findViewById(R.id.foodDescription);
        foodPrice = itemView.findViewById(R.id.foodPrice);
        btn_add_to_cart = itemView.findViewById(R.id.btn_add_to_cart);

        // Set the OnClickListener to the entire view.
        btn_add_to_cart.setOnClickListener(this);
        this.clickListener = clickListener;
    }

    public void bind(Food food) {

        foodName.setText(food.getName());
        foodDescription.setText(food.getDescription());
        foodPrice.setText(food.getPrice().toString());
        this.food = food;
    }

    public static FoodViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list_item, parent, false);
        return new FoodViewHolder(view, new ClickListener() {
            @Override
            public void addToCart(int p, CartItem cartItem) {
                DataStore ds = DataStore.getCart();
                ds.cart.add(cartItem);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_to_cart:
                CartItem cartItem = new CartItem(this.food, 1);
                clickListener.addToCart(this.getLayoutPosition(), cartItem);
                break;
            default:
                break;
        }
    }
}
