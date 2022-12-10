package com.example.tuseats.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuseats.utils.DataStore;
import com.example.tuseats.R;
import com.example.tuseats.activity.Cart;
import com.example.tuseats.model.CartItem;

public class CartListAdapter extends ListAdapter<CartItem, CartListAdapter.CartViewHolder> {
    private Context mActivityContext;

    public CartListAdapter(@NonNull DiffUtil.ItemCallback<CartItem> diffCallback, Context context) {
        super(diffCallback);
        this.mActivityContext = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
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

    public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView foodName;
        private TextView totalPrice;
        private TextView cart_item_quantity;
        private TextView cart_empty_text;
        private Button checkout_button;
        private Button btn_remove_from_cart;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.cart_food_name);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            btn_remove_from_cart = itemView.findViewById(R.id.btn_remove_from_cart);
            cart_item_quantity = itemView.findViewById(R.id.cart_item_quantity);
            cart_empty_text = itemView.findViewById(R.id.cart_empty_textview);
            checkout_button = itemView.findViewById(R.id.checkout_button);

            btn_remove_from_cart.setOnClickListener(this);
        }

        public void bind(CartItem cart) {
            Double total = cart.getQuantity() * cart.getFood().getPrice();

            foodName.setText(cart.getFood().getName());
            totalPrice.setText("€ " + total);
            cart_item_quantity.setText("Quantity: " + cart.getQuantity());
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_remove_from_cart) {
                removeAt(this.getAdapterPosition());
            }
        }

        public void removeAt(int position) {
            DataStore.getCart().cart.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, DataStore.getCart().cart.size());
            if (DataStore.getCart().cart.isEmpty()) {
                Cart.setCart_empty_text();
            }
        }
    }
}
