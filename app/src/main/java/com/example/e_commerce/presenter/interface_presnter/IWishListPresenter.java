package com.example.e_commerce.presenter.interface_presnter;

public interface IWishListPresenter {
    void onSendRequestForData(String api_token);
    void onDeleteFavourite(String api_token, int id);
    void onAddToCart(String api_token, int id);
    void cancelCall();
}
