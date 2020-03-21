package com.example.e_commerce.presenter;

import com.example.e_commerce.iEcommerce.IorderInteractor;
import com.example.e_commerce.presenter.interface_presnter.ICashOnDelivery;
import com.example.e_commerce.view.view_interface.ICashOnDeliveryView;
import com.example.e_commerce.view.view_interface.IMyOrderView;

public class CashOnDeliveryPresenter implements ICashOnDelivery {
    private ICashOnDeliveryView View;
    private IorderInteractor iorderInteractor;

    public CashOnDeliveryPresenter(IorderInteractor iorderInteractor,ICashOnDeliveryView View ) {
        this.View = View;
        this.iorderInteractor = iorderInteractor;
    }

    @Override
    public void createOrder(String api_token, String address_id) {
        iorderInteractor.addOrder(api_token,address_id);
    }
}
