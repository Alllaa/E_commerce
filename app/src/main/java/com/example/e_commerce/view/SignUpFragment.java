package com.example.e_commerce.view;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.example.e_commerce.LanguageHandling;
import com.example.e_commerce.R;
import com.example.e_commerce.presenter.interface_presnter.ISignUpPresenter;
import com.example.e_commerce.view.view_interface.ISignUpView;

import com.example.e_commerce.view.view_interface.ImainActivity;
import com.example.e_commerce.model.UserInfo;
import com.example.e_commerce.model.UserInteractor;
import com.example.e_commerce.presenter.SignUpPresenter;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpFragment extends Fragment implements ISignUpView {

/*
    private EditText name;
    private EditText phone;
    private EditText email;
    //private EditText password;*/
    private TextInputEditText name;
    private TextInputEditText phone;
    private TextInputEditText email;
    private TextInputEditText password;

    private Button signUP;

    private ImainActivity imainActivity;
    SharedPreferences sharedPreferences;


    private ISignUpPresenter iSignUpPresenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        goHomeIfLogged();

        imainActivity.setAppBarTitle("Sign up");


        iSignUpPresenter = new SignUpPresenter(new UserInteractor() ,this);
        name = view.findViewById(R.id.name_signup);
        email = view.findViewById(R.id.email_signup);
        phone = view.findViewById(R.id.phone_signup);
        password = view.findViewById(R.id.signup_password);

        signUP = view.findViewById(R.id.signUp_button);

        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Name = name.getText().toString();
                String Email = email.getText().toString();
                String Phone = phone.getText().toString();
                String Password = password.getText().toString();
                if(Name.length() == 0 || Email.length() ==0 || Phone.length() == 0 || Password.length() == 0){
                    Snackbar.make(MainActivity.coordinatorLayout, "all fields are required", BaseTransientBottomBar.LENGTH_LONG).setDuration(5000)
                            .setBackgroundTint(Color.rgb(200,0,0)).setTextColor(Color.rgb(255, 255, 255)).show();
                }else if(Phone.length() < 11 && Phone.length() != 0 ){
                    Snackbar.make(MainActivity.coordinatorLayout, "enter a valid phone number", BaseTransientBottomBar.LENGTH_LONG).setDuration(5000)
                            .setBackgroundTint(Color.rgb(200,0,0)).setTextColor(Color.rgb(255, 255, 255)).show();
                } else {
                    Log.d("rrrr", Name + "  " + Email);
                    hideKeyboard(getActivity());
                    imainActivity.setBrogressBarVisible(true);
                    iSignUpPresenter.SignUp(Name, Email, Phone, Password);
                }
            }
        });

        navigate(view);

        return view;
    }

    private void navigate(View view){
        (view.findViewById(R.id.signin_text))

                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        imainActivity.setBrogressBarVisible(false);

                        Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_signinFragment);
                    }
                });
    }

    private void goHomeIfLogged(){
        if(sharedPreferences.getBoolean("is_logged",false)){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("is_logged", true).apply();
            editor.commit();

            Intent intent = new Intent(getActivity() , HomeActivity.class);
            intent.putExtra("api_token","");
            startActivity(intent);
            getActivity().finish();

            // set the language
            String lang = LanguageHandling.getLanguage(getActivity());
            if (lang != null) {
                LanguageHandling.ChangeLanguage(lang, getActivity());
            }
        }
    }
    @Override
    public void navigateToHome(boolean signedIn , UserInfo userInfo) {
        if(signedIn){
            imainActivity.setBrogressBarVisible(false);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("is_logged", true).apply();
            editor.putString("api_token",userInfo.getApi_token()).apply();
            editor.putString("name",userInfo.getName()).apply();
            editor.putString("email",userInfo.getEmail()).apply();
            editor.putString("profilePic",userInfo.getProfilePic()).apply();
            editor.commit();

            Intent intent = new Intent(getActivity() , HomeActivity.class);
            intent.putExtra("api_token",userInfo.getApi_token());
            startActivity(intent);
            getActivity().finish();
        }
    }

    @Override
    public void hideProgresBar() {
        imainActivity.setBrogressBarVisible(false);
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
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}