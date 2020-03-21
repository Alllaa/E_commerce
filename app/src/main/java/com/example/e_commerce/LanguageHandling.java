package com.example.e_commerce;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Locale;

public class LanguageHandling {

    private static String language;
    private static SharedPreferences sharedPreferences;

    public static void ChangeLanguage(String lang,Context context){
        language = lang;
        Locale myLocale = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    public static void saveLanguage(Context context){
        sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lang",language).commit();
    }

    public static String getLanguage(Context context){

        sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);

        if(sharedPreferences.contains("lang")){
            return sharedPreferences.getString("lang","");
        }

        return null;
    }

}
