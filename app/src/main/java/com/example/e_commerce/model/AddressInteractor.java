package com.example.e_commerce.model;

import android.util.Log;
import android.widget.Toast;

import com.example.e_commerce.MyApplication;
import com.example.e_commerce.iEcommerce.IAddressInteractor;
import com.example.e_commerce.presenter.interface_presnter.IMyAddressPresenter;
import com.example.e_commerce.rest.ApiError;
import com.example.e_commerce.rest.EcommerceServices;
import com.example.e_commerce.rest.ErrorBody;
import com.example.e_commerce.rest.ErrorMessage;
import com.example.e_commerce.rest.RestClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressInteractor implements IAddressInteractor {


    @Override
    public void createAddress(Address address,String api_token) {
        Log.d("aaaaa",api_token);
        EcommerceServices apiService = RestClient.getClient().create(EcommerceServices.class);
        Call<Object> calling = apiService.createAddress(address,api_token);

        calling.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(!response.isSuccessful() ){
                    Log.d("TAG","ERROR :  "+    response.raw().body().toString());
                    ErrorMessage error = ApiError.parseError(response);
                    String errorMessage="";
                    for(ErrorBody message : error.getErrors()) {
                        Log.d("TAG", message.getName() + " : " + message.getMessage());
                        errorMessage += message.getMessage()+"\n";
                    }
                    Toast.makeText(MyApplication.getInstance().getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();
                }
                else if(response.isSuccessful() && response.body()!= null) {

                    Log.d("fff",response.body().toString());
                }
            }
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("TAG", "Got error : " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void getAddresses(String api_token, final IMyAddressPresenter presenter) {


        Log.d("aaaaa",api_token);
        EcommerceServices apiService = RestClient.getClient().create(EcommerceServices.class);
        Call<List<Address>> calling = apiService.getAddresses(api_token);

        calling.enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                if(!response.isSuccessful() ){
                    Log.d("TAG","ERROR :  "+    response.raw().body().toString());
                    /*ErrorMessage error = ApiError.parseError(response);
                    String errorMessage="";
                    for(ErrorBody message : error.getErrors()) {
                        Log.d("TAG", message.getName() + " : " + message.getMessage());
                        errorMessage += message.getMessage()+"\n";
                    }
                    Toast.makeText(MyApplication.getInstance().getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();*/
                }
                else if(response.isSuccessful() && response.body()!= null) {
                    presenter.returnAddresses(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {
                Log.e("TAG", "Got error : " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void editAddress(Address address) {

    }
}
