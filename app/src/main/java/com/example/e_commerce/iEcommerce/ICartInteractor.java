package com.example.e_commerce.iEcommerce;

import com.example.e_commerce.presenter.interface_presnter.ICartPresenter;

public interface ICartInteractor {

    void getCart(String api_token, ICartPresenter presenter);
    void updateCartQuantity(String api_token, int product_id, int state);
    void deleteProductFromCart(String api_token, int product_id);
}
