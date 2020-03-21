package com.example.e_commerce.presenter;

import com.example.e_commerce.presenter.interface_presnter.ISignInPresenter;
import com.example.e_commerce.view.view_interface.ISignInView;
import com.example.e_commerce.iEcommerce.Iinteractor;
import com.example.e_commerce.model.UserInfo;

public class SignInPresenter implements ISignInPresenter {

    private Iinteractor interactor;
    private ISignInView iSignInView;

    public SignInPresenter(Iinteractor interactor, ISignInView iSignInView) {
        this.interactor = interactor;
        this.iSignInView = iSignInView;
    }

    @Override
    public void SignIn(String name , String password) {
        UserInfo user = new UserInfo(name,"","", password);
        interactor.signInUser(user,this);
    }
    @Override
    public void isSignedIn(boolean check,UserInfo userInfo) {
        if (check) {
            iSignInView.navigateToHome(check,userInfo);
        }
    }

    @Override
    public void hideProgressbar() {
        iSignInView.hideProgresBar();
    }

}

