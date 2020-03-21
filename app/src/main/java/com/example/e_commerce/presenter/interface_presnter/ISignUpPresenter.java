package com.example.e_commerce.presenter.interface_presnter;

import com.example.e_commerce.model.User;
import com.example.e_commerce.model.UserInfo;

public interface ISignUpPresenter {

    void SignUp(String name ,String email , String phone , String password) ;
    void isSignedIn(boolean check, UserInfo userInfo);
    void hideProgressbar();
}
