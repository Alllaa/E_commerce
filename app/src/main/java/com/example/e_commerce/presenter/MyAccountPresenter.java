package com.example.e_commerce.presenter;

import android.util.Log;

import com.example.e_commerce.iEcommerce.Iinteractor;
import com.example.e_commerce.presenter.interface_presnter.ImyAccountPresenter;
import com.example.e_commerce.view.view_interface.ImyAccountView;
import com.example.e_commerce.model.User;

public class MyAccountPresenter implements ImyAccountPresenter {


    private Iinteractor interactor;
    private ImyAccountView imyaccountView;

    public MyAccountPresenter(Iinteractor interactor, ImyAccountView imyaccountView) {
        this.interactor = interactor;
        this.imyaccountView = imyaccountView;
    }
    @Override
    public void displayaccount(String api_token) {

        interactor.getProfile(api_token,this);
    }

    @Override
    public void getUser(User user) {
        Log.d("TAG User",user.getUser().getName());
        imyaccountView.displayuser(user);
    }
}
