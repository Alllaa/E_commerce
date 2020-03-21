package com.example.e_commerce.presenter;

import com.example.e_commerce.model.BestSeller;
import com.example.e_commerce.model.GetDataFromServer;
import com.example.e_commerce.iEcommerce.IGetDataFromServer;
import com.example.e_commerce.model.TopCategories;
import com.example.e_commerce.presenter.interface_presnter.IDealsPresenter;
import com.example.e_commerce.view.view_interface.IDeals;

import java.util.List;

public class DealsPresenter implements IDealsPresenter {
    // private IGetDeals iGetDeals;
    private IGetDataFromServer iGetDeals;
    private IDeals iDeals;
    private List<TopCategories> adds;
    private List<TopCategories> topCategoriesList;
    private List<TopCategories> bestSellersList;

    public DealsPresenter(IDeals newDeals) {
        iDeals = newDeals;
        iGetDeals = new GetDataFromServer();
    }

    @Override
    public void sendDeals(List<TopCategories> ads, List<TopCategories> topCategories, List<TopCategories> bestSellers) {
        adds = ads;
        topCategoriesList = topCategories;
        bestSellersList = bestSellers;
        if (iDeals != null) {
            iDeals.onDataRecieved(ads, topCategories, bestSellers);
        }
    }

    @Override
    public void cancelCall() {
        iGetDeals.cancelCall();
    }

    @Override
    public void onSendData() {
        iGetDeals.getDealsData(this);
    }


}
