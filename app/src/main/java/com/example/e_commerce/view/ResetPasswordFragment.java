package com.example.e_commerce.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.view.view_interface.IchangePasswordView;
import com.example.e_commerce.view.view_interface.ImainActivity;
import com.example.e_commerce.model.UserInteractor;
import com.example.e_commerce.presenter.ResetPasswordPresenter;


public class ResetPasswordFragment extends Fragment implements IchangePasswordView {

    private Button done ;
    private ResetPasswordPresenter mResetPasswordPresenter;
    private EditText newPassword;
    private EditText RenewPassword;

    private ImainActivity imainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        imainActivity.setAppBarTitle("Forgot password");

        Bundle bundle = getArguments();

        final String phone=bundle.getString("phone"); final int code=bundle.getInt("code");
        Log.d("ddddd",bundle.getString("phone")+bundle.getInt("code"));

        mResetPasswordPresenter = new ResetPasswordPresenter(new UserInteractor(),this);

        done = view.findViewById(R.id.done);

        newPassword = view.findViewById(R.id.new_password);
        RenewPassword = view.findViewById(R.id.reNew_password);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call api
                if(newPassword.getText().toString().equals(RenewPassword.getText().toString()))
                    mResetPasswordPresenter.resetPassword(phone,code,newPassword.getText().toString());
                else Toast.makeText(getActivity(),"Password not match",Toast.LENGTH_SHORT).show();
                //nav to home
            }
        });

        return view;
    }
    @Override
    public void navigateToHome(boolean signedIn,String api_token) {
        if(signedIn){
            Intent intent = new Intent(getActivity() , HomeActivity.class);
            intent.putExtra("api_token",api_token);
            startActivity(intent);
            getActivity().finish();
        }
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ImainActivity) {
            imainActivity = (ImainActivity) context;
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        imainActivity = null;
    }
}