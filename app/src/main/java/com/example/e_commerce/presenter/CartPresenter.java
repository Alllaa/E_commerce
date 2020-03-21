package com.example.e_commerce.presenter;

import com.example.e_commerce.iEcommerce.ICartInteractor;
import com.example.e_commerce.model.Cart_items;
import com.example.e_commerce.presenter.interface_presnter.ICartPresenter;
import com.example.e_commerce.view.view_interface.ICartView;

import java.util.List;

public class CartPresenter implements ICartPresenter {

    private ICartView cartView;
    private ICartInteractor cartInteractor;

    public CartPresenter(ICartView cartView, ICartInteractor cartInteractor) {
        this.cartView = cartView;
        this.cartInteractor = cartInteractor;
    }

    @Override
    public void getCart(String api_token) {

        cartInteractor.getCart(api_token,this);

    }

    @Override
    public void returnCart(Cart_items cart) {
        cartView.displayCarts(cart);
    }

    @Override
    public void updateCart(String api_token, int product_id, int state) {
        cartInteractor.updateCartQuantity(api_token,product_id,state);
    }

    @Override
    public void deleteproduct(String api_token, int product_id) {
        cartInteractor.deleteProductFromCart(api_token,product_id);
    }
}
