package com.example.tuseats.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuseats.Adapter.FoodListAdapter;
import com.example.tuseats.R;
import com.example.tuseats.activity.Cart;
import com.example.tuseats.viewModel.FoodViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodListFragment extends Fragment {

    private FoodViewModel mFoodViewModel;
    private TextView foodSectionTitle;
    private FloatingActionButton button_cart;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FoodListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodListFragment newInstance(String param1, String param2) {
        FoodListFragment fragment = new FoodListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food_list, container, false);
        //Setting the food section title
        foodSectionTitle = view.findViewById(R.id.foodSectionTitle);
        Intent intent = getActivity().getIntent();
        String title = intent.getStringExtra("title");
        foodSectionTitle.setText(title);

        RecyclerView recyclerView = view.findViewById(R.id.foodListRecyclerView);
        final FoodListAdapter adapter = new FoodListAdapter(new FoodListAdapter.FoodDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mFoodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        mFoodViewModel.getAllFoodsByFoodSection(title).observe(getActivity(), foods -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(foods);
        });

        // cart button
        button_cart = view.findViewById(R.id.fab);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            button_cart.setVisibility(View.INVISIBLE);
        } else {
            button_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_cart = new Intent(getContext(), Cart.class);
                    startActivity(intent_cart);
                }
            });
        }
        return view;
    }
}