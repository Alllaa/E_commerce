package com.example.e_commerce.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.e_commerce.LanguageHandling;
import com.example.e_commerce.MyApplication;
import com.example.e_commerce.R;
import com.example.e_commerce.view.HomeActivity;
import com.example.e_commerce.view.view_interface.IHomeActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Locale;

public class ChangeLanguageFragment extends Fragment {

    private CheckBox englishcheck;
    private CheckBox arabicCheck;
    IHomeActivity mListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_language, container, false);


        englishcheck = view.findViewById(R.id.english_check);
        arabicCheck = view.findViewById(R.id.arabic_check);



        String l = LanguageHandling.getLanguage(getActivity());
        if(l != null){
            if(l.equals("en")) {
                englishcheck.setChecked(true);
                arabicCheck.setChecked(false);
            }
            else if(l.equals("ar")) {
                englishcheck.setChecked(false);
                arabicCheck.setChecked(true);
            }
        }
        else {
            englishcheck.setChecked(true);
            arabicCheck.setChecked(false);
        }

        englishcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    arabicCheck.setChecked(false);
                }
                else if(!b){
                    arabicCheck.setChecked(true);
                }
            }
        });

        arabicCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    englishcheck.setChecked(false);

                }
                else if(!b){
                    englishcheck.setChecked(true);
                }
            }
        });

        view.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(arabicCheck.isChecked()){
                    LanguageHandling.ChangeLanguage("ar",getActivity());
                    mListener.changeLanguageOMenue("ar");
                }
                else if(englishcheck.isChecked()){
                    LanguageHandling.ChangeLanguage("en",getActivity());
                    mListener.changeLanguageOMenue("en");
                }
                LanguageHandling.saveLanguage(getActivity());
                Navigation.findNavController(view).navigate(R.id.action_changeLanguageFragment_to_myAccountFragment);
                Snackbar.make(HomeActivity.coordinatorLayout,getActivity().getString(R.string.message_success_change_lang), BaseTransientBottomBar.LENGTH_SHORT).setBackgroundTint(Color./*rgb(109, 175, 73)*/rgb(100, 196, 111))
                        .show();
            }
        });
        return  view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IHomeActivity) {
            mListener = (IHomeActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
}