package com.example.e_commerce.presenter.interface_presnter;

import com.example.e_commerce.model.TopCategories;

import java.util.List;

public interface IHeadLinePresenter {
    void onSendData();
    void sendCategories(List<TopCategories> topCategories);

}
