package com.example.e_commerce.presenter.interface_presnter;

import com.example.e_commerce.model.Cart_items;

import java.util.List;

public interface ICartPresenter {

    void getCart(String api_token);
    void returnCart(Cart_items cart);
    void updateCart(String api_token, int product_id, int state);
    void deleteproduct(String api_token, int product_id);
}
