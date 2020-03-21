package com.example.e_commerce.presenter;

import android.util.Log;

import com.example.e_commerce.presenter.interface_presnter.IUpdateProfilePresenter;
import com.example.e_commerce.view.view_interface.IUpdateProfileView;
import com.example.e_commerce.iEcommerce.Iinteractor;

public class UpdateProfilePresenter implements IUpdateProfilePresenter {

    private Iinteractor interactor;
    private IUpdateProfileView iUpdateProfileView;


    public UpdateProfilePresenter(Iinteractor interactor, IUpdateProfileView iUpdateProfileView) {
        this.interactor = interactor;
        this.iUpdateProfileView = iUpdateProfileView;
    }

    @Override
    public void updateProfile(String api_token,String name, String email,String path){
//        Log.d("TAG",path);
        interactor.updateProfile(api_token,name , email ,path,this);
    }

    @Override
    public void nav() {
        iUpdateProfileView.navigate();
    }
}
