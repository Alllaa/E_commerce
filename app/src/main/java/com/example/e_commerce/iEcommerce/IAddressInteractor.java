package com.example.e_commerce.iEcommerce;

import com.example.e_commerce.model.Address;
import com.example.e_commerce.presenter.interface_presnter.IMyAddressPresenter;

public interface IAddressInteractor {

    void createAddress(Address address,String api_token);
    void getAddresses(String api_token, IMyAddressPresenter presenter);
    void editAddress(Address address);
}
