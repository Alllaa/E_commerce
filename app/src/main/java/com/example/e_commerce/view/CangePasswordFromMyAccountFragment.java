package com.example.e_commerce.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.e_commerce.R;
import com.example.e_commerce.view.view_interface.IchangePasswordFromMyAccount;
import com.example.e_commerce.model.UserInteractor;
import com.example.e_commerce.presenter.ChangePasswordFromMyAccountPresenter;

public class CangePasswordFromMyAccountFragment extends Fragment implements IchangePasswordFromMyAccount {

    ChangePasswordFromMyAccountPresenter presenter;
    SharedPreferences sharedPreferences ;
    private EditText oldpasswrd;
    private EditText newpasswrd;
    private EditText renewpasswrd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cange_password_from_my_account, container, false);

        presenter = new ChangePasswordFromMyAccountPresenter(new UserInteractor(),this);

        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);


        oldpasswrd = view.findViewById(R.id.old_password);
        newpasswrd = view.findViewById(R.id.new_passwordd);
        renewpasswrd = view.findViewById(R.id.re_new_password);

        view.findViewById(R.id.update_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(newpasswrd.getText().toString().equals(renewpasswrd.getText().toString()) && oldpasswrd.getText().length() > 5)
                {
                    presenter.updatePassword(oldpasswrd.getText().toString(),
                            newpasswrd.getText().toString(),
                            sharedPreferences.getString("api_token","")
                    );

                    getActivity().onBackPressed();
                }
            }
        });

        return view;
    }

}