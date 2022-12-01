package com.example.tuseats.Adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.tuseats.ViewHolder.FoodSectionViewHolder;
import com.example.tuseats.model.FoodSection;

import java.util.List;

public class FoodSectionListAdapter extends ListAdapter<FoodSection, FoodSectionViewHolder> {
    private List<Integer> imageIDs;

    public FoodSectionListAdapter(@NonNull DiffUtil.ItemCallback<FoodSection> diffCallback, List<Integer> imageIDs) {
        super(diffCallback);
        this.imageIDs = imageIDs;
    }

    @Override
    public FoodSectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return FoodSectionViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(FoodSectionViewHolder holder, int position) {
        FoodSection current = getItem(position);
        holder.bind(current, this.getCurrentList(), imageIDs);
    }

    public static class FoodSectionDiff extends DiffUtil.ItemCallback<FoodSection> {

        @Override
        public boolean areItemsTheSame(@NonNull FoodSection oldItem, @NonNull FoodSection newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull FoodSection oldItem, @NonNull FoodSection newItem) {
            return oldItem.getFoodSectionName().equals(newItem.getFoodSectionName());
        }
    }
}
