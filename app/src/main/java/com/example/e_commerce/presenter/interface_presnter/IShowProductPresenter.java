package com.example.e_commerce.presenter.interface_presnter;

import com.example.e_commerce.model.Product;

public interface IShowProductPresenter {
    void onSendData(String api_token,int num);
    void sendGetProduct(Product product);
    void onAddToWish_List(String api_token, int product_id);
    void cancelCall();
    void onAddToCart(String apiToken,int productId,String size_id,String color_id,int quantity);
    void onRateTheProduct(String api_token,int product_id,int rate);
}
