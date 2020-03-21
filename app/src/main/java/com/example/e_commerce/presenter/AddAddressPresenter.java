package com.example.e_commerce.presenter;

import com.example.e_commerce.view.view_interface.IAddAddressView;
import com.example.e_commerce.model.Address;
import com.example.e_commerce.model.AddressInteractor;

public class AddAddressPresenter {

    private AddressInteractor interactor;
    private IAddAddressView view;

    public AddAddressPresenter(AddressInteractor interactor, IAddAddressView view) {
        this.interactor = interactor;
        this.view = view;
    }

    public void createAddress(String C, String S, String B, String F, String A, String L, String P, String N, String api_token){

        interactor.createAddress(new Address(C,S,B,F,A,L,P,N),api_token);
    }
}
