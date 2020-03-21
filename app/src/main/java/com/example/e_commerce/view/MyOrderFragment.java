package com.example.e_commerce.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.e_commerce.R;
import com.example.e_commerce.adapter.OrderAdapter;
import com.example.e_commerce.model.Order;
import com.example.e_commerce.model.OrderInteractor;
import com.example.e_commerce.presenter.MyOrderPresenter;
import com.example.e_commerce.presenter.interface_presnter.IMyOrderPresenter;
import com.example.e_commerce.view.view_interface.IHomeActivity;
import com.example.e_commerce.view.view_interface.IMyOrderView;

import java.util.ArrayList;
import java.util.List;

public class MyOrderFragment extends Fragment implements IMyOrderView {
//85
    private List<Order> list = new ArrayList<>();
    private IMyOrderPresenter myOrderPresenter;
    private SharedPreferences sharedPreferences;
    private IHomeActivity mListener;

    private RecyclerView ordersRecycler;
    private OrderAdapter orderAdapter = new OrderAdapter(getActivity(),list);

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_order, container, false);

        mListener.setProgressBarVisible(true);
        mListener.onChangeInFragment(getActivity().getString(R.string.my_orders_title));

        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        myOrderPresenter = new MyOrderPresenter(new OrderInteractor(),this);
        myOrderPresenter.getOrders(sharedPreferences.getString("api_token",""));

        ordersRecycler = view.findViewById(R.id.my_orders_recycler);
        ordersRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        return view;
    }

    @Override
    public void displayOrders(List<Order> orders) {

        if(orders != null){
            mListener.setProgressBarVisible(false);
            list.clear();
            list.addAll(orders);
            ordersRecycler.setAdapter(orderAdapter);
        }
        if(orders.size() == 0){
            view.findViewById(R.id.no_orders_found).setVisibility(View.VISIBLE);
        }
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