package com.example.e_commerce.view.view_interface;

import com.example.e_commerce.model.BestSeller;
import com.example.e_commerce.model.NewArrivals;

import java.util.List;

public interface IArrival_ISeller {
    void onNewArrivalRecieved(List<NewArrivals> newArrivals);

    void onBestSellerRecieved(List<BestSeller> bestSellers);
}
