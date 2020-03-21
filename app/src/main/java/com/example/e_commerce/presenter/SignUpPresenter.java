package com.example.e_commerce.presenter;

import com.example.e_commerce.presenter.interface_presnter.ISignUpPresenter;
import com.example.e_commerce.view.view_interface.ISignUpView;
import com.example.e_commerce.iEcommerce.Iinteractor;
import com.example.e_commerce.model.UserInfo;

public class SignUpPresenter implements ISignUpPresenter {

    private Iinteractor interactor;
    private ISignUpView iSignUpView;

    public SignUpPresenter(Iinteractor interactor, ISignUpView iSignUpView) {
        this.interactor = interactor;
        this.iSignUpView = iSignUpView;
    }

    @Override
    public void SignUp(String name ,String email , String phone , String password ) {
        UserInfo user = new UserInfo(name, email, phone, password);
        interactor.signUpUser(user,this);
    }

    @Override
    public void isSignedIn(boolean check,UserInfo userInfo) {
        if(check){
            iSignUpView.navigateToHome(check,userInfo);
        }
    }

    @Override
    public void hideProgressbar() {
        iSignUpView.hideProgresBar();
    }
}
