package com.example.tuseats.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuseats.R;
import com.example.tuseats.model.CartItem;

public class CartViewHolder extends RecyclerView.ViewHolder {
    private TextView foodName;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        foodName = itemView.findViewById(R.id.cart_food_name);
    }

    public void bind(CartItem cart) {
        foodName.setText(cart.getFood().getName());
    }

    public static CartViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }
}
