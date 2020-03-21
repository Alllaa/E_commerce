package com.example.e_commerce.presenter.interface_presnter;

import com.example.e_commerce.model.BestSeller;
import com.example.e_commerce.model.NewArrivals;

import java.util.List;

public interface IArrivalSellerPresenter {
    void onSendDoubleData(String api_token);
    void onAddToFavourite(String api_token,int productId);
    void sendBestSeller(List<BestSeller> bestSellers);
    void sendNewArrivals(List<NewArrivals> newArrivals);

}
