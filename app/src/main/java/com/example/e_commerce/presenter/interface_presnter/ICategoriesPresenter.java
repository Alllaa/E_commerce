package com.example.e_commerce.presenter.interface_presnter;

import com.example.e_commerce.model.NewArrivals;

import java.util.List;

public interface ICategoriesPresenter {
    void onSendData(int num,String api_token);
    void addToDavourite(String api_token,int id);
    void sendcategories(List<NewArrivals> newArrivals);
    void getProductForSearch(String api_token,String key_word);

}
