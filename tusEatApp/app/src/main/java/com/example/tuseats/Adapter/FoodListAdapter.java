package com.example.tuseats.Adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.tuseats.ViewHolder.FoodViewHolder;
import com.example.tuseats.model.Food;

public class FoodListAdapter extends ListAdapter<Food, FoodViewHolder> {
    public FoodListAdapter(@NonNull DiffUtil.ItemCallback<Food> diffCallback) {
        super(diffCallback);
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return FoodViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        Food current = getItem(position);
        holder.bind(current);
    }

    public static class FoodDiff extends DiffUtil.ItemCallback<Food> {

        @Override
        public boolean areItemsTheSame(@NonNull Food oldItem, @NonNull Food newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Food oldItem, @NonNull Food newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
}
