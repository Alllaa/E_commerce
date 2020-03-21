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

import com.example.e_commerce.R;
import com.example.e_commerce.view.view_interface.ImainActivity;


public class VerificationCodePasswordFragment extends Fragment {

    private Button verify;
    private EditText _1st;
    private EditText _2nd;
    private EditText _3rd;
    private EditText _4th;
    private ImainActivity imainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_verification_code_password, container, false);

        imainActivity.setAppBarTitle("Forgot password");

        _1st = view.findViewById(R.id.firstfield);
        _2nd = view.findViewById(R.id.secondfield);
        _3rd = view.findViewById(R.id.thirdfield);
        _4th = view.findViewById(R.id.fourthfield);
        final Bundle bundle = getArguments();

        if(bundle != null){
            String code  = Integer.toString(bundle.getInt("code"));
            Log.d("asas",code);
            _1st.setText(code.charAt(0)+"");
            _2nd.setText(code.charAt(1)+"");
            _3rd.setText(code.charAt(2)+"");
            _4th.setText(code.charAt(3)+"");
            Log.d("ddddd",bundle.getString("phone")+bundle.getInt("code"));

        }

        verify = view.findViewById(R.id.verifyButton);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_verificationCodePasswordFragment_to_changePasswordFragment,bundle);
            }
        });

        return view;
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