package com.example.tuseats.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tuseats.R;
import com.example.tuseats.model.Food;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView foodName;
    private TextView foodDescription;
    private TextView foodPrice;

    private FoodViewHolder(View itemView) {
        super(itemView);
        // Initialize the views.
        foodName = itemView.findViewById(R.id.foodName);
        foodDescription = itemView.findViewById(R.id.foodDescription);
        foodPrice = itemView.findViewById(R.id.foodPrice);

        // Set the OnClickListener to the entire view.
        itemView.setOnClickListener(this);
    }

    public void bind(Food food) {

        foodName.setText(food.getName());
        foodDescription.setText(food.getDescription());
        foodPrice.setText(food.getPrice().toString());
    }

    public static FoodViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list_item, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onClick(View view) {

//        FoodSection curFoodSection = foodSectionList.get(getAdapterPosition());
//        Intent detailIntent = new Intent(mContext, FoodList.class);
//        detailIntent.putExtra("title", curFoodSection.getFoodSectionName());
////        detailIntent.putExtra("image_resource",
////                curFoodSection.getFoodSectionImage());
//        mContext.startActivity(detailIntent);
    }
}
