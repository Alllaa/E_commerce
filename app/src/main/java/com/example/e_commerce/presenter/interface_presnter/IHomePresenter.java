package com.example.e_commerce.presenter.interface_presnter;

import com.example.e_commerce.model.BestSeller;
import com.example.e_commerce.model.NewArrivals;
import com.example.e_commerce.model.TopCategories;

import java.util.List;

public interface IHomePresenter {
    void onSendData(String api_token);
    void onAddToWish_List(String api_token, int product_id);
    void sendHome(List<NewArrivals> newArrivals, List<TopCategories> topCategories, List<BestSeller> bestSellers);
    void cancelCall();

}
