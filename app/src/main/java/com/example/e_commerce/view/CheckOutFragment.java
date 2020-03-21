package com.example.e_commerce.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.MyApplication;
import com.example.e_commerce.R;
import com.example.e_commerce.adapter.cart_adapter;
import com.example.e_commerce.model.Address;
import com.example.e_commerce.model.AddressInteractor;
import com.example.e_commerce.model.Cart_items;
import com.example.e_commerce.presenter.CheckOutPresenter;
import com.example.e_commerce.presenter.interface_presnter.IMyAddressPresenter;
import com.example.e_commerce.view.view_interface.ICheckOutView;
import com.example.e_commerce.view.view_interface.IHomeActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class CheckOutFragment extends Fragment implements ICheckOutView {


    List<Cart_items.Cart_object> carts = new ArrayList<>();
    private RecyclerView cartRecycler;
    private cart_adapter cartAdapter = new cart_adapter(getActivity(), carts, 1);

    private TextView totlaPrice;
    private TextView shipping;
    private TextView Total;
    private TextView itemsNum;
    private TextView adress_numver;
    private TextView address;
    private Button changeAddress;

    Cart_items cart = null;
    Bundle bundle = new Bundle();
    IHomeActivity mListener;

    IMyAddressPresenter presenter;

    private List<Address> addresses = new ArrayList<>();
    int counter = 1;
    String address_id = "";
    SharedPreferences sharedPreferences;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_check_out, container, false);

        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

        presenter = new CheckOutPresenter(new AddressInteractor(), this);
        presenter.getAddresses(sharedPreferences.getString("api_token", ""));
        mListener.onChangeInFragment(getActivity().getString(R.string.check_out_title));


        totlaPrice = view.findViewById(R.id.items_price);
        shipping = view.findViewById(R.id.shipping_price);
        Total = view.findViewById(R.id.total_price);
        itemsNum = view.findViewById(R.id.items_quantity);
        adress_numver = view.findViewById(R.id.adress_number);
        address = view.findViewById(R.id.address);
        changeAddress = view.findViewById(R.id.change_address);

        cartRecycler = view.findViewById(R.id.shipment_recycler);
        cartRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        bundle = getArguments();

        cart = bundle.getParcelable("cart");
        displayCarts();

        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToAddAddress();
            }
        };

        view.findViewById(R.id.continue_payment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addresses.size() == 0) {
                    Snackbar.make(HomeActivity.coordinatorLayout, getString(R.string.no_address), BaseTransientBottomBar.LENGTH_SHORT)
                            .setBackgroundTint(Color.rgb(255, 0, 0))
                            .setAction("Add", listener)
                            .setActionTextColor(Color.rgb(250, 250, 250))
                            .setDuration(5000)
                            .show();
                } else {
                    Navigation.findNavController(view).navigate(R.id.action_checkOutFragment_to_continueCheckOut, bundle);
                }
            }
        });
        changeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (addresses != null && counter < addresses.size()) {
                    adress_numver.setText("Address " + (counter + 1));
                    address.setText(addresses.get(counter).getBuilding() + "," + addresses.get(counter).getAtreet() + "," + addresses.get(counter).getCity());
                    address_id = addresses.get(counter).getId();
                    bundle.putString("address_id", address_id);
                    counter++;

                    if (counter == addresses.size()) counter = 0;
                } else navigateToAddAddress();
            }

        });

        return view;
    }

    private void navigateToAddAddress() {
        Navigation.findNavController(view).navigate(R.id.action_checkOutFragment_to_addAddressFragment);
    }

    private void displayCarts() {

        carts.clear();
        carts.addAll(cart.getCarts());
        cartRecycler.setAdapter(cartAdapter);

        totlaPrice.setText("USD " + cart.getTotal_price());
        shipping.setText("USD " + cart.getShiping());
        Total.setText("USD " + (cart.getTotal_price() + cart.getShiping()));

        if (cart.getTotal_items() == 1) itemsNum.setText(cart.getTotal_items() + " item");
        else if (cart.getTotal_items() > 1) itemsNum.setText(cart.getTotal_items() + " items");
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
    public void displayAddresses(List<Address> lis) {
        addresses.clear();
        addresses.addAll(lis);

        if (lis.size() != 0) {
            adress_numver.setText("Address 1");
            address.setText(addresses.get(0).getBuilding() + "," + addresses.get(0).getAtreet() + "," + addresses.get(0).getCity());
            view.findViewById(R.id.relative5).setVisibility(View.VISIBLE);
            view.findViewById(R.id.progress).setVisibility(View.GONE);
            changeAddress.setVisibility(View.VISIBLE);
            address_id = addresses.get(0).getId();
            bundle.putString("address_id", address_id);
            if (lis.size() > 1) ;
            else if (lis.size() == 1) changeAddress.setEnabled(false);

        } else {
            view.findViewById(R.id.relative5).setVisibility(View.VISIBLE);
            view.findViewById(R.id.progress).setVisibility(View.GONE);
            changeAddress.setVisibility(View.VISIBLE);

            address.setVisibility(View.GONE);
            adress_numver.setText(getString(R.string.no_address));
            changeAddress.setEnabled(true);
            changeAddress.setText(getString(R.string.add_my_new_address_button));
        }
    }
}