package com.example.e_commerce.presenter;

import com.example.e_commerce.model.BestSeller;
import com.example.e_commerce.model.GetDataFromServer;
import com.example.e_commerce.iEcommerce.IGetDataFromServer;
import com.example.e_commerce.model.NewArrivals;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.presenter.interface_presnter.IArrivalSellerPresenter;
import com.example.e_commerce.view.view_interface.IArrival_ISeller;

import java.util.List;

public class ArrivalSellerPresenter implements IArrivalSellerPresenter,GetDataFromServer.GetFavourite {

    private IGetDataFromServer iGetFavourite;
    private IGetDataFromServer iGetBestSeller;
    private IGetDataFromServer iGetNewArrivals;
    private IArrival_ISeller iArrival_iSeller;

    private List<NewArrivals> newArrivalsList;
    private List<BestSeller> bestSellersList;

    public ArrivalSellerPresenter(IArrival_ISeller newIArrival_iSeller) {

        iArrival_iSeller = newIArrival_iSeller;
        iGetBestSeller = new GetDataFromServer();
        iGetNewArrivals = new GetDataFromServer();
        iGetFavourite = new GetDataFromServer();
    }


    @Override
    public void onSendDoubleData(String api_token) {
            iGetNewArrivals.getNewArrivalsData(this,api_token);
            iGetBestSeller.getBestSellerData(this,api_token);
    }

    @Override
    public void onAddToFavourite(String api_token, int productId) {
        iGetFavourite.deleteFromFavourite(this,api_token,productId);
    }

    @Override
    public void sendBestSeller(List<BestSeller> bestSellers) {
            bestSellersList = bestSellers;
        if (iArrival_iSeller != null) {
            iArrival_iSeller.onBestSellerRecieved(bestSellersList);
        }
    }

    @Override
    public void sendNewArrivals(List<NewArrivals> newArrivals) {
        newArrivalsList = newArrivals;
        if (iArrival_iSeller != null)
        {
            iArrival_iSeller.onNewArrivalRecieved(newArrivalsList);
        }
    }

    @Override
    public void sendFavourtieProduct(List<Product> product) {

    }
}
