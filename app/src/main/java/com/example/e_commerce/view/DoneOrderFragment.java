package com.example.e_commerce.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_commerce.R;
import com.example.e_commerce.adapter.cart_adapter;
import com.example.e_commerce.model.Cart_items;
import com.example.e_commerce.view.view_interface.IHomeActivity;

import java.util.ArrayList;
import java.util.List;

public class DoneOrderFragment extends Fragment {

    List<Cart_items.Cart_object> carts = new ArrayList<>();
    private RecyclerView cartRecycler;
    private cart_adapter cartAdapter = new cart_adapter(getActivity(),carts,1);
    IHomeActivity mListener;
    Cart_items cart = null;
    Bundle bundle = new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_done_order, container, false);


        bundle = getArguments();
        cart = bundle.getParcelable("cart");
        cartRecycler = view.findViewById(R.id.done_recycler);
        cartRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        carts.clear();
        carts.addAll(cart.getCarts());
        cartRecycler.setAdapter(cartAdapter);


        view.findViewById(R.id.track_order_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_doneOrderFragment_to_myOrderFragment,bundle);
            }
        });
        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IHomeActivity) {
            mListener = (IHomeActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}