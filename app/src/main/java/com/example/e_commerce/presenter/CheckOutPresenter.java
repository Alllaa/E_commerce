package com.example.e_commerce.presenter;

import com.example.e_commerce.iEcommerce.IAddressInteractor;
import com.example.e_commerce.model.Address;
import com.example.e_commerce.model.AddressInteractor;
import com.example.e_commerce.presenter.interface_presnter.IMyAddressPresenter;
import com.example.e_commerce.view.view_interface.ICheckOutView;

import java.util.List;

public class CheckOutPresenter implements IMyAddressPresenter {

    private IAddressInteractor interactor;
    private ICheckOutView view;

    public CheckOutPresenter(IAddressInteractor interactor, ICheckOutView view) {
        this.interactor = interactor;
        this.view = view;
    }

    @Override
    public void getAddresses(String api_token) {
        interactor.getAddresses(api_token,this);
    }

    @Override
    public void returnAddresses(List<Address> list){
            view.displayAddresses(list);
    }

}
