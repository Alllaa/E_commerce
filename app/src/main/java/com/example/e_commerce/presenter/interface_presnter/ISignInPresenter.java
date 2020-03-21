package com.example.e_commerce.presenter.interface_presnter;

import com.example.e_commerce.model.UserInfo;

public interface ISignInPresenter
{
    void SignIn(String name ,String password) ;
    void isSignedIn(boolean check, UserInfo userInfo);
    void hideProgressbar();
}
