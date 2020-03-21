package com.example.e_commerce.iEcommerce;

import com.example.e_commerce.model.UserInfo;
import com.example.e_commerce.presenter.interface_presnter.IResetPasswordPresenter;
import com.example.e_commerce.presenter.interface_presnter.ISendResetPasswordCodePresenter;
import com.example.e_commerce.presenter.interface_presnter.ISignInPresenter;
import com.example.e_commerce.presenter.interface_presnter.ISignUpPresenter;
import com.example.e_commerce.presenter.interface_presnter.IUpdateProfilePresenter;
import com.example.e_commerce.presenter.interface_presnter.ImyAccountPresenter;

public interface Iinteractor {

    void signUpUser(UserInfo user, ISignUpPresenter presenter);
    void signInUser(UserInfo user, ISignInPresenter presenter);
    void sendResetPasswordCode(ISendResetPasswordCodePresenter p, String Phone);
    void resetPsssword(String phone, int ressetcode, String newPassword, IResetPasswordPresenter presenter);
    void getProfile(String api_token, ImyAccountPresenter imyAccountPresenter);
    void updatePassword(String oldPassword , String newPassword,String api_token);
    void updateProfile(String api_token, String name, String email, String path, IUpdateProfilePresenter presenter);
}
