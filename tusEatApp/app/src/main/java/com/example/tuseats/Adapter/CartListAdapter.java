package com.example.tuseats.Adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.tuseats.ViewHolder.CartViewHolder;
import com.example.tuseats.model.CartItem;

public class CartListAdapter extends ListAdapter<CartItem, CartViewHolder> {
    public CartListAdapter(@NonNull DiffUtil.ItemCallback<CartItem> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CartViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem curCartItem = getItem(position);
        holder.bind(curCartItem);
    }

    public static class CartDiff extends DiffUtil.ItemCallback<CartItem> {

        @Override
        public boolean areItemsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
            return oldItem.getFood().getName().equals(newItem.getFood().getName());
        }
    }
}
