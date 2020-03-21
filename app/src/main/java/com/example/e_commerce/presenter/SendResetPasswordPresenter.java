package com.example.e_commerce.presenter;

import com.example.e_commerce.presenter.interface_presnter.ISendResetPasswordCodePresenter;
import com.example.e_commerce.view.view_interface.ISendResetPasswordCodeView;
import com.example.e_commerce.iEcommerce.Iinteractor;

public class SendResetPasswordPresenter implements ISendResetPasswordCodePresenter {
    private Iinteractor interactor;
    private ISendResetPasswordCodeView view;

    public SendResetPasswordPresenter(Iinteractor interactor, ISendResetPasswordCodeView view) {
        this.interactor = interactor;
        this.view = view;
    }
public SendResetPasswordPresenter(){}

    public void sendResetCode(String phone){
        interactor.sendResetPasswordCode(this,phone);
}

    @Override
    public void getCodeandPhone(int code, String phone) {
        view.sendCodeandPhone(code,phone);
    }
}
