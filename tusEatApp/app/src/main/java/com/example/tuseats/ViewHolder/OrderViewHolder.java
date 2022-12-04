package com.example.tuseats.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuseats.R;
import com.example.tuseats.model.Order;

public class OrderViewHolder extends RecyclerView.ViewHolder {
    private TextView dateOrdered;
    private TextView totalPriceOrdered;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        dateOrdered = itemView.findViewById(R.id.order_date);
        totalPriceOrdered = itemView.findViewById(R.id.order_total_price);
    }

    public void bind(Order order) {
        dateOrdered.setText(order.getDateOrdered().toString());
        totalPriceOrdered.setText("€" + order.getTotalPrice());
    }

    public static OrderViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_item, parent, false);
        return new OrderViewHolder(view);
    }
}
