package com.example.e_commerce.presenter;

import com.example.e_commerce.view.view_interface.IcontactUsView;
import com.example.e_commerce.model.ContactInteractor;

public class ContactUsPresenter
{
    private ContactInteractor interactor;
    private IcontactUsView icontactUsView;

    public ContactUsPresenter(ContactInteractor interactor, IcontactUsView icontactUsView) {
        this.interactor = interactor;
        this.icontactUsView = icontactUsView;
    }

    public void SendMessage(String name,String email,String message){
        interactor.contactUs(name,email, message);
    }
}
