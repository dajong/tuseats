package com.example.tuseats.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuseats.R;
import com.example.tuseats.model.CartItem;

public class CartViewHolder extends RecyclerView.ViewHolder {
    private TextView foodName;
    private TextView totalPrice;
    private TextView cart_item_quantity;

    private Button btn_remove_from_cart;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        foodName = itemView.findViewById(R.id.cart_food_name);
        totalPrice = itemView.findViewById(R.id.totalPrice);
        btn_remove_from_cart = itemView.findViewById(R.id.btn_remove_from_cart);
        cart_item_quantity = itemView.findViewById(R.id.cart_item_quantity);
    }

    public void bind(CartItem cart) {
        Double total = cart.getQuantity() * cart.getFood().getPrice();

        foodName.setText(cart.getFood().getName());
        totalPrice.setText("€ " + total);
        cart_item_quantity.setText("Quantity: " + cart.getQuantity());
    }

    public static CartViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }
}
