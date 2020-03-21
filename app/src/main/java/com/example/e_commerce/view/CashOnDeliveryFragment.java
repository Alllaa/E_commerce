package com.example.e_commerce.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.e_commerce.R;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.e_commerce.model.Cart_items;
import com.example.e_commerce.model.OrderInteractor;
import com.example.e_commerce.presenter.CashOnDeliveryPresenter;
import com.example.e_commerce.presenter.interface_presnter.ICashOnDelivery;
import com.example.e_commerce.view.view_interface.ICashOnDeliveryView;


public class CashOnDeliveryFragment extends Fragment implements ICashOnDeliveryView {

    private TextView totlaPrice;
    private TextView shipping;
    private TextView Total;
    private TextView itemsNum;

    Cart_items cart = null;
    Bundle bundle = new Bundle();

    private ICashOnDelivery presenter;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cash_on_delivery, container, false);

        presenter = new CashOnDeliveryPresenter(new OrderInteractor(), this);

        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

        totlaPrice = view.findViewById(R.id.items_price);
        shipping = view.findViewById(R.id.shipping_price);
        Total = view.findViewById(R.id.total_price);
        itemsNum = view.findViewById(R.id.items_quantity);

        bundle = getArguments();

        if (bundle != null) {
            cart = bundle.getParcelable("cart");

            totlaPrice.setText("USD " + cart.getTotal_price());
            shipping.setText("USD " + cart.getShiping());
            Total.setText("USD " + (cart.getTotal_price() + cart.getShiping()));

        }
        if (cart.getTotal_items() == 1) itemsNum.setText(cart.getTotal_items() + " item");
        else if (cart.getTotal_items() > 1) itemsNum.setText(cart.getTotal_items() + " items");


        view.findViewById(R.id.place_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.createOrder(sharedPreferences.getString("api_token", ""), bundle.getString("address_id", ""));
                Navigation.findNavController(view).navigate(R.id.action_cashOnDeliveryFragment_to_doneOrderFragment, bundle);

            }
        });
        return view;
    }
}