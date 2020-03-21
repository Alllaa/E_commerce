package com.example.e_commerce.presenter.interface_presnter;

import com.example.e_commerce.model.Address;

import java.util.List;

public interface IMyAddressPresenter {

    void getAddresses(String api_token);
    void returnAddresses(List<Address> list);

}
