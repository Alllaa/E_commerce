package com.example.e_commerce.iEcommerce;

import com.example.e_commerce.model.Product;
import com.example.e_commerce.presenter.interface_presnter.IArrivalSellerPresenter;
import com.example.e_commerce.presenter.interface_presnter.ICategoriesPresenter;
import com.example.e_commerce.presenter.interface_presnter.IDealsPresenter;
import com.example.e_commerce.presenter.interface_presnter.IHeadLinePresenter;
import com.example.e_commerce.presenter.interface_presnter.IHomePresenter;
import com.example.e_commerce.presenter.interface_presnter.IShowProductPresenter;

import java.util.List;

public interface IGetDataFromServer {

    void getProductsData(ICategoriesPresenter getList, int num, String api_token);

    void getCategoriesData(IHeadLinePresenter getList);

    void getDealsData(IDealsPresenter getList);

    void getHomeData(IHomePresenter getList, String api_token);

    void addTofavourit(String api_token, int product_id);

    void getBestSellerData(IArrivalSellerPresenter getList, String api_token);

    void getNewArrivalsData(IArrivalSellerPresenter getList, String api_token);

    void getGetProductData(IShowProductPresenter getList, String api_token, int num);

    void postProductToCart(IShowProductPresenter getList, String api_token, int productId, String color_id, String size_id, int quantity);


    void getMyFavouriteFromSever(GetFavourite getList, String api_token);

    void deleteFromFavourite(GetFavourite getList, String api_token, int product_id);

    void buy(GetFavourite getList, String api_token, int productId);

    void getFroSearch(String api_token, String key,ICategoriesPresenter presenter);

    void sendReview(String api_token, int product, int rate);

    void cancelCall();

    interface GetFavourite {
        void sendFavourtieProduct(List<Product> product);
    }
}
