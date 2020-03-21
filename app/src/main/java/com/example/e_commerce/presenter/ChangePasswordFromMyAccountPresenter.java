package com.example.e_commerce.presenter;

import com.example.e_commerce.view.view_interface.IchangePasswordFromMyAccount;
import com.example.e_commerce.iEcommerce.Iinteractor;

public class ChangePasswordFromMyAccountPresenter {

    private Iinteractor interactor;
    private IchangePasswordFromMyAccount ichangePassword;

    public ChangePasswordFromMyAccountPresenter(Iinteractor interactor, IchangePasswordFromMyAccount ichangePassword) {
        this.interactor = interactor;
        this.ichangePassword = ichangePassword;
    }

    public void updatePassword(String oldPassword,String newPasword,String api_token){
        interactor.updatePassword(oldPassword ,newPasword,api_token);
    }
}
