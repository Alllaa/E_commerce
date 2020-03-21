package com.example.e_commerce.presenter;

import com.example.e_commerce.model.GetDataFromServer;
import com.example.e_commerce.iEcommerce.IGetDataFromServer;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.presenter.interface_presnter.IWishListPresenter;
import com.example.e_commerce.view.view_interface.IWishListView;

import java.util.List;

public class WishListPresenter implements IWishListPresenter, GetDataFromServer.GetFavourite {
    IGetDataFromServer iGetFavourite;
    IWishListView iWishListView;

    List<Product>productList;
    public WishListPresenter(IWishListView newWishListView)
    {
           iGetFavourite = new GetDataFromServer();
           iWishListView = newWishListView;
    }
    @Override
    public void onSendRequestForData(String api_token) {
        iGetFavourite.getMyFavouriteFromSever(this,api_token);
    }

    @Override
    public void onDeleteFavourite(String api_token, int id) {
        iGetFavourite.deleteFromFavourite(this,api_token,id);
    }

    @Override
    public void onAddToCart(String api_token, int id) {
        iGetFavourite.buy(this,api_token,id);
    }

    @Override
    public void cancelCall() {
        iGetFavourite.cancelCall();
    }

    @Override
    public void sendFavourtieProduct(List<Product> product) {
        productList = product;
        if (iWishListView != null)
        {
            iWishListView.onFavouritesRevieved(product);
        }
    }
}
