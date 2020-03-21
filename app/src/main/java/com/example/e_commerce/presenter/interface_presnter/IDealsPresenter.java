package com.example.e_commerce.presenter.interface_presnter;

import com.example.e_commerce.model.BestSeller;
import com.example.e_commerce.model.TopCategories;

import java.util.List;

public interface IDealsPresenter {
    void onSendData();
    void sendDeals(List<TopCategories> ads, List<TopCategories> topCategories, List<TopCategories> bestSellers);
    void cancelCall();

}
