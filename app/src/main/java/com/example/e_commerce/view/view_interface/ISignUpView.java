package com.example.e_commerce.view.view_interface;

import com.example.e_commerce.model.UserInfo;

public interface ISignUpView {

    void navigateToHome(boolean signedIn, UserInfo userInfo);
    void hideProgresBar();

}
