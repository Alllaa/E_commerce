package com.example.e_commerce.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_commerce.R;
import com.example.e_commerce.view.view_interface.IcontactUsView;
import com.example.e_commerce.model.ContactInteractor;
import com.example.e_commerce.presenter.ContactUsPresenter;
import com.google.android.material.textfield.TextInputEditText;

public class ContactUsFragment extends Fragment implements IcontactUsView {

    ContactUsPresenter contactUsPresenter;
    private TextInputEditText name;
    private TextInputEditText email;
    private TextInputEditText message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        contactUsPresenter = new ContactUsPresenter(new ContactInteractor(),this);

        name = view.findViewById(R.id.name_contact);
        email = view.findViewById(R.id.email_contact);
        message = view.findViewById(R.id.message_contact);

        view.findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().length() !=0  && email.getText().length() != 0 && message.getText().length() != 0){
                    contactUsPresenter.SendMessage(name.getText().toString(),email.getText().toString(),message.getText().toString());
                    getActivity().onBackPressed();
                }

            }
        });
        return view;
    }
}