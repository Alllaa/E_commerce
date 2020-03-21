package com.example.e_commerce.view.view_interface;

import com.example.e_commerce.model.BestSeller;
import com.example.e_commerce.model.TopCategories;

import java.util.List;

public interface IDeals {
    void onDataRecieved(List<TopCategories> ads, List<TopCategories> topCategories, List<TopCategories> bestSellers);

}
