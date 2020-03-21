package com.example.e_commerce.presenter;

import com.example.e_commerce.presenter.interface_presnter.IMyAddressPresenter;
import com.example.e_commerce.view.view_interface.IMyAddressView;
import com.example.e_commerce.model.Address;
import com.example.e_commerce.model.AddressInteractor;

import java.util.List;

public class MyAddresspresenter implements IMyAddressPresenter {

    private AddressInteractor interactor;
    private IMyAddressView view;

    public MyAddresspresenter(AddressInteractor interactor, IMyAddressView view) {
        this.interactor = interactor;
        this.view = view;
    }

    @Override
    public void getAddresses(String api_token) {
        interactor.getAddresses(api_token,this);
    }

    @Override
    public void returnAddresses(List<Address> list) {
        view.displayAddresses(list);
    }
}
