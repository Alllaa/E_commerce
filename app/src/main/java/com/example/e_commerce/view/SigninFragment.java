package com.example.e_commerce.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.e_commerce.R;
import com.example.e_commerce.presenter.interface_presnter.ISignInPresenter;
import com.example.e_commerce.view.view_interface.ISignInView;
import com.example.e_commerce.view.view_interface.ImainActivity;
import com.example.e_commerce.model.UserInfo;
import com.example.e_commerce.model.UserInteractor;
import com.example.e_commerce.presenter.SignInPresenter;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;


public class SigninFragment extends Fragment implements ISignInView {

    private ISignInPresenter iSignInPresenter;
    /*private EditText name;
    private EditText password;*/
    private Button signIN;

    private TextInputEditText name;
    private TextInputEditText password;

    private ImainActivity imainActivity;
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        goHomeIfLogged();


       imainActivity.setAppBarTitle("Sign in");

        iSignInPresenter = new SignInPresenter(new UserInteractor() , this);


        name = view.findViewById(R.id.name_signin);
        password = view.findViewById(R.id.signin_password);
        signIN = view.findViewById(R.id.button_signin);

        signIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Name = name.getText().toString();
                String Password = password.getText().toString();
                if(Name.equals(" ") || password.equals(" ") || Name.length()<1 || Password.length()<1){
                    hideKeyboard(getActivity());
                    Snackbar.make(getActivity().findViewById(R.id.snack_bar),"Please enter required fields", BaseTransientBottomBar.LENGTH_SHORT).show();

                }
                else {
                    hideKeyboard(getActivity());
                    imainActivity.setBrogressBarVisible(true);
                    iSignInPresenter.SignIn(Name, Password);
           }
            }
        });
        (view.findViewById(R.id.signup_text))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Navigation.findNavController(view).navigate(R.id.action_signinFragment_to_signUpFragment);
                    }
                });

        (view.findViewById(R.id.forgot_password)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_signinFragment_to_forgotPasswordFragment);

            }
        });

        return view;
    }
    private void goHomeIfLogged(){
        if(sharedPreferences.getBoolean("is_logged",false)){
            imainActivity.setBrogressBarVisible(false);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("is_logged", true).apply();
            editor.commit();

            Intent intent = new Intent(getActivity() , HomeActivity.class);
            intent.putExtra("api_token","");
            startActivity(intent);
            getActivity().finish();
        }
    }
    @Override
    public void navigateToHome(boolean signedIn , UserInfo userInfo) {
        if(signedIn){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("is_logged", true).apply();
            editor.putString("name",userInfo.getName());
            editor.putString("api_token",userInfo.getApi_token());
            editor.putString("email",userInfo.getEmail());
            editor.putString("profilePic",userInfo.getProfilePic());
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