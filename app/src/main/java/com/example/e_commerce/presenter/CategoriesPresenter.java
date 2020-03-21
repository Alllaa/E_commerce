package com.example.e_commerce.presenter;

import com.example.e_commerce.model.GetDataFromServer;
import com.example.e_commerce.iEcommerce.IGetDataFromServer;
import com.example.e_commerce.model.NewArrivals;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.presenter.interface_presnter.ICategoriesPresenter;
import com.example.e_commerce.view.view_interface.ICategories;

import java.util.List;

public class CategoriesPresenter implements ICategoriesPresenter,GetDataFromServer.GetFavourite {
   // private IGetAllProduct iGetAllProduct;
    private IGetDataFromServer iGetFavourite;
    private IGetDataFromServer iGetAllProduct;
    private ICategories iCategories;
    List<NewArrivals>products;

    public CategoriesPresenter(ICategories newCategories)
    {
        iGetFavourite = new GetDataFromServer();
        iGetAllProduct = new GetDataFromServer();
        iCategories = newCategories;
    }
    @Override
    public void onSendData(int num,String api_token) {
        iGetAllProduct.getProductsData(this,num, api_token);
    }

    @Override
    public void addToDavourite(String api_token, int id) {
        iGetFavourite.deleteFromFavourite(this,api_token,id);
    }


    @Override
    public void sendcategories(List<NewArrivals> newArrivals) {
        products = newArrivals;
        if (iCategories != null)
        {
            iCategories.onDataRecieved(products);
        }
    }

    @Override
    public void getProductForSearch(String api_token ,String key_word) {
        iGetAllProduct.getFroSearch(api_token,key_word,this);
    }

    @Override
    public void sendFavourtieProduct(List<Product> product) {

    }
}
