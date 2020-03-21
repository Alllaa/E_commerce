package com.example.e_commerce.presenter;

import com.example.e_commerce.iEcommerce.IorderInteractor;
import com.example.e_commerce.model.Order;
import com.example.e_commerce.presenter.interface_presnter.IMyOrderPresenter;
import com.example.e_commerce.view.view_interface.IMyOrderView;

import java.util.List;

public class MyOrderPresenter implements IMyOrderPresenter {

    private IMyOrderView myOrderView;
    private IorderInteractor iorderInteractor;

    public MyOrderPresenter(IorderInteractor iorderInteractor,IMyOrderView myOrderView ) {
        this.myOrderView = myOrderView;
        this.iorderInteractor = iorderInteractor;
    }



    @Override
    public void getOrders(String api_token) {

        iorderInteractor.getOrders(api_token,this);
    }

    @Override
    public void returnOrders(List<Order> orders) {

        myOrderView.displayOrders(orders);
    }
}
