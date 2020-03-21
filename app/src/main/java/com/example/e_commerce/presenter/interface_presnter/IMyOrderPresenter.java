package com.example.e_commerce.presenter.interface_presnter;

import com.example.e_commerce.model.Order;

import java.util.List;

public interface IMyOrderPresenter {

    void getOrders(String api_token);
    void returnOrders(List<Order> orders);
}
