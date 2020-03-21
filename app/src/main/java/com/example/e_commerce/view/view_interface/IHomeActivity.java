package com.example.e_commerce.view.view_interface;

import android.content.Context;

public interface IHomeActivity {
    void onChangeToolbarTitle(String title);
    void onChangeInMyAccount(String title);
    void onChangeInFragment(String title);
    void onCahngeShowProduct(String title);
    void setProgressBarVisible(boolean b2);
    void changeLanguageOMenue(String lang);
}
