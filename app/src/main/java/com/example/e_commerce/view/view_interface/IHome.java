package com.example.e_commerce.view.view_interface;

import com.example.e_commerce.model.BestSeller;
import com.example.e_commerce.model.NewArrivals;
import com.example.e_commerce.model.TopCategories;

import java.util.List;

public interface IHome {
    void onDataRecieved(List<NewArrivals> newArrivals, List<TopCategories> topCategories, List<BestSeller> bestSellers);

}
