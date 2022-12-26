package com.example.tuseats.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuseats.R;
import com.example.tuseats.model.Order;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.Map;

public class OrderListAdapter extends FirestoreRecyclerAdapter<Order, OrderListAdapter.OrderViewHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public OrderListAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_item, parent, false);

        return new OrderViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Order model) {
        Map<String, Integer> food_items = model.getFoodOrdered();

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : food_items.entrySet()) {
            sb.append(entry.getValue() + " - " + entry.getKey() + "\n");
        }

        holder.order_date.setText(model.getDateOrdered());
        holder.order_total_price.setText("€ " + model.getPriceOrdered());
        holder.order_items.setText(sb.toString());
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView order_date, order_total_price, order_items;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            order_date = itemView.findViewById(R.id.order_date);
            order_total_price = itemView.findViewById(R.id.order_total_price);
            order_items = itemView.findViewById(R.id.order_items);
        }
    }
}
