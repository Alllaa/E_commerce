package com.example.e_commerce.model;

import android.util.Log;
import android.widget.Toast;

import com.example.e_commerce.MyApplication;
import com.example.e_commerce.apiResponses.ChangePasswordMessage;
import com.example.e_commerce.rest.ApiError;
import com.example.e_commerce.rest.EcommerceServices;
import com.example.e_commerce.rest.ErrorBody;
import com.example.e_commerce.rest.ErrorMessage;
import com.example.e_commerce.rest.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactInteractor {

    public void contactUs(String name,String email, String message) {
        EcommerceServices apiService = RestClient.getClient().create(EcommerceServices.class);
        Call<Object> calling = apiService.contactUs(name,email,message);

        calling.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(!response.isSuccessful() ){
                    ErrorMessage error = ApiError.parseError(response);
                    String errorMessage="";
                    for(ErrorBody message : error.getErrors()) {
                        Log.d("TAG", message.getName() + " : " + message.getMessage());
                        errorMessage += message.getMessage()+"\n";
                    }
                    errorMessage = errorMessage.substring(0,errorMessage.length()-1);
//                    Toast.makeText(MyApplication.getInstance().getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();
                }
                else if(response.isSuccessful() ) {
                    //Log.d("koko",response.body().toString());
//                    Toast.makeText(MyApplication.getInstance().getApplicationContext(), "message sent",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("TAG", "Got error : " + t.getLocalizedMessage());
                if(t.getLocalizedMessage().contains("Failed to connect")){
//                    Toast.makeText(MyApplication.getInstance().getApplicationContext(),"Connection error... try later",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
