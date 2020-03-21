package com.example.e_commerce.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.view.view_interface.ISendResetPasswordCodeView;
import com.example.e_commerce.view.view_interface.ImainActivity;
import com.example.e_commerce.model.UserInteractor;
import com.example.e_commerce.presenter.SendResetPasswordPresenter;

public class ForgotPasswordFragment extends Fragment implements ISendResetPasswordCodeView {

    private Button sendReset;
    private SendResetPasswordPresenter sendResetPasswordPresenter;
    private EditText phone;
    View view;
    private ImainActivity imainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

       imainActivity.setAppBarTitle("Forgot password");
        phone = view.findViewById(R.id.phone_forgot);
        sendResetPasswordPresenter = new SendResetPasswordPresenter(new UserInteractor() , this);

        sendReset = view.findViewById(R.id.forgot_button);
        sendReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phone.getText().length()==11) {
                    sendResetPasswordPresenter.sendResetCode(phone.getText().toString());

                }
                else {
                    Toast.makeText(getActivity(),"Please enter valid phone",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void sendCodeandPhone(int code, String phone) {

        Bundle bundle = new Bundle();
        bundle.putInt("code",code);
        bundle.putString("phone",phone);
        Log.d("ooooo",bundle.getString("phone")+"    "+bundle.getInt("code"));
        Navigation.findNavController(view).navigate(R.id.action_forgotPasswordFragment_to_verificationCodePasswordFragment,bundle);


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