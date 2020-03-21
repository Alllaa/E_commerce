package com.example.e_commerce.presenter;

import com.example.e_commerce.presenter.interface_presnter.IResetPasswordPresenter;
import com.example.e_commerce.view.view_interface.IchangePasswordView;
import com.example.e_commerce.iEcommerce.Iinteractor;

public class ResetPasswordPresenter  implements IResetPasswordPresenter {
    private Iinteractor interactor;
    private IchangePasswordView view;

    public ResetPasswordPresenter(Iinteractor interactor, IchangePasswordView view) {
        this.interactor = interactor;
        this.view = view;
    }

    public void resetPassword(String phone, int code, String newPassword) {
        interactor.resetPsssword(phone,code,newPassword,this);
    }
    @Override
    public void isSignedIn(boolean check,String api_token) {
        if (check) {
            view.navigateToHome(check,api_token);
        }
    }
}
