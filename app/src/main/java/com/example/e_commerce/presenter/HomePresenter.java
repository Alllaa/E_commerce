package com.example.e_commerce.presenter;

import com.example.e_commerce.model.BestSeller;
import com.example.e_commerce.model.GetDataFromServer;
import com.example.e_commerce.iEcommerce.IGetDataFromServer;
import com.example.e_commerce.model.NewArrivals;
import com.example.e_commerce.model.TopCategories;
import com.example.e_commerce.presenter.interface_presnter.IHomePresenter;
import com.example.e_commerce.view.view_interface.IHome;

import java.util.List;

public class HomePresenter implements IHomePresenter {

    private IGetDataFromServer iGetHome;
    private IHome iHome;
    private List<NewArrivals> newArrivalsList;
    private List<TopCategories> topCategoriesList;
    private List<BestSeller> bestSellersList;


    public HomePresenter(IHome newHome) {
        iGetHome = new GetDataFromServer();
        iHome = newHome;
    }

    @Override
    public void onSendData(String api_token) {

       iGetHome.getHomeData(this,api_token);
    }

    @Override
    public void onAddToWish_List(String api_token, int product_id) {
        iGetHome.addTofavourit(api_token,product_id);
    }

    @Override
    public void sendHome(List<NewArrivals> newArrivals, List<TopCategories> topCategories, List<BestSeller> bestSellers) {
        newArrivalsList = newArrivals;
        topCategoriesList = topCategories;
        bestSellersList = bestSellers;
        if (iHome != null) {
            iHome.onDataRecieved(newArrivals, topCategories, bestSellers);
        }
    }

    @Override
    public void cancelCall() {
        iGetHome.cancelCall();
    }
}
