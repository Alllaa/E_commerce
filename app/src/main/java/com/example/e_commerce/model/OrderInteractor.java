package com.example.e_commerce.model;

import android.util.Log;
import android.widget.Toast;

import com.example.e_commerce.MyApplication;
import com.example.e_commerce.iEcommerce.IorderInteractor;
import com.example.e_commerce.presenter.interface_presnter.IMyOrderPresenter;
import com.example.e_commerce.rest.ApiError;
import com.example.e_commerce.rest.EcommerceServices;
import com.example.e_commerce.rest.ErrorBody;
import com.example.e_commerce.rest.ErrorMessage;
import com.example.e_commerce.rest.RestClient;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderInteractor implements IorderInteractor {


    @Override
    public void getOrders(String api_token, final IMyOrderPresenter presenter) {
        EcommerceServices apiService = RestClient.getClient().create(EcommerceServices.class);
        Call<List<Order>> calling = apiService.getOrders(api_token);

        calling.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if(!response.isSuccessful() ){
                    ErrorMessage error = ApiError.parseError(response);
                    String errorMessage="";
                    for(ErrorBody message : error.getErrors()) {
                        Log.d("TAG", message.getName() + " : " + message.getMessage());
                        errorMessage += message.getMessage()+"\n";
                    }
                    errorMessage = errorMessage.substring(0,errorMessage.length()-1);
                    Toast.makeText(MyApplication.getInstance().getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();
                }
                else if(response.isSuccessful() ) {
                    presenter.returnOrders(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.e("TAG", "Got error : " + t.getMessage());
                if(t.getMessage()!= null && t.getMessage().contains("Failed to connect")){
                    //Toast.makeText(MyApplication.getInstance().getApplicationContext(),"Connection error... try later",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void addOrder(String api_token,String address_id) {
        EcommerceServices apiService = RestClient.getClient().create(EcommerceServices.class);
        Call<Void> calling = apiService.createOrder(api_token,address_id,"1");

        calling.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful() ){
                    ErrorMessage error = ApiError.parseError(response);
                    String errorMessage="";
                    for(ErrorBody message : error.getErrors()) {
                        Log.d("TAG", message.getName() + " : " + message.getMessage());
                        errorMessage += message.getMessage()+"\n";
                    }
                    errorMessage = errorMessage.substring(0,errorMessage.length()-1);
                    Toast.makeText(MyApplication.getInstance().getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();
                }
                else if(response.isSuccessful() ) {
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("TAG", "Got error : " + t.getMessage());
                if(t.getMessage()!= null && t.getMessage().contains("Failed to connect")){
                    //Toast.makeText(MyApplication.getInstance().getApplicationContext(),"Connection error... try later",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
