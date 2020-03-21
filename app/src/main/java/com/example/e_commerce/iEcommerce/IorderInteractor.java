package com.example.e_commerce.iEcommerce;

import com.example.e_commerce.model.Order;
import com.example.e_commerce.presenter.interface_presnter.IMyOrderPresenter;

import java.util.List;

public interface IorderInteractor {

    void getOrders(String api_token, IMyOrderPresenter presenter);
    void addOrder(String api_token,String address_id);

}
