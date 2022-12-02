package com.example.tuseats.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tuseats.FoodList;
import com.example.tuseats.R;
import com.example.tuseats.model.FoodSection;

import java.util.List;

public class FoodSectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView foodSectionItemView;
    private TextView foodSectionDescription;
    private ImageView foodSectionImage;
    private Context mContext;
    private List<FoodSection> foodSectionList;
    private List<Integer> imageIDs;

    private FoodSectionViewHolder(View itemView) {
        super(itemView);
        foodSectionItemView = itemView.findViewById(R.id.foodSectionTitle);
        foodSectionDescription = itemView.findViewById(R.id.foodSectionDescription);
        foodSectionImage = itemView.findViewById(R.id.foodSectionImage);
        mContext = itemView.getContext();
        // Set the OnClickListener to the entire view.
        itemView.setOnClickListener(this);

    }

    public void bind(FoodSection foodSection, List<FoodSection> foodSectionList, List<Integer> imageIDs) {

        foodSectionItemView.setText(foodSection.getFoodSectionName());
        foodSectionDescription.setText(foodSection.getShortDescription());

        // Load the images into the ImageView using the Glide library.
        Glide.with(mContext).load(imageIDs.get(foodSection.getImageID() - 1)).into(foodSectionImage);

        this.foodSectionList = foodSectionList;
        this.imageIDs = imageIDs;
    }

    public static FoodSectionViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_section_list_item, parent, false);
        return new FoodSectionViewHolder(view);
    }

    @Override
    public void onClick(View view) {

        FoodSection curFoodSection = foodSectionList.get(getAdapterPosition());
        Intent detailIntent = new Intent(mContext, FoodList.class);
        detailIntent.putExtra("title", curFoodSection.getFoodSectionName());
        mContext.startActivity(detailIntent);
    }
}
