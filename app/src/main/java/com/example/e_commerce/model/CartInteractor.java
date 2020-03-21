package com.example.e_commerce.model;

import android.util.Log;
import android.widget.Toast;

import com.example.e_commerce.MyApplication;
import com.example.e_commerce.iEcommerce.ICartInteractor;
import com.example.e_commerce.presenter.interface_presnter.ICartPresenter;
import com.example.e_commerce.rest.ApiError;
import com.example.e_commerce.rest.EcommerceServices;
import com.example.e_commerce.rest.ErrorBody;
import com.example.e_commerce.rest.ErrorMessage;
import com.example.e_commerce.rest.RestClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartInteractor implements ICartInteractor {
    @Override
    public void getCart(String api_token, final ICartPresenter presenter) {

        EcommerceServices apiService = RestClient.getClient().create(EcommerceServices.class);
        Call<Cart_items> calling = apiService.getCart(api_token);

        calling.enqueue(new Callback<Cart_items>() {
            @Override
            public void onResponse(Call<Cart_items> call, Response<Cart_items> response) {
                if(!response.isSuccessful() ){
                    Log.d("TAG","ERROR :  "+    response.raw().body().toString());
                    ErrorMessage error = ApiError.parseError(response);
                    String errorMessage="";
                    for(ErrorBody message : error.getErrors()) {
                        Log.d("TAG", message.getName() + " : " + message.getMessage());
                        errorMessage += message.getMessage()+"\n";
                    }
//                    Toast.makeText(MyApplication.getInstance().getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();
                }
                else if(response.isSuccessful() && response.body()!= null) {

                    presenter.returnCart(response.body());

                }
            }
            @Override
            public void onFailure(Call<Cart_items> call, Throwable t) {
                Log.e("TAG", "Got error : " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void updateCartQuantity(String api_token, int product_id, int state) {

        EcommerceServices apiService = RestClient.getClient().create(EcommerceServices.class);
        Call<Object> call = apiService.updateQuantity(api_token,state,product_id);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if (response.isSuccessful())
                {
//                    Toast.makeText(MyApplication.getInstance().getApplicationContext(), "the quatity is updated", Toast.LENGTH_LONG).show();
                }
                else if (!response.isSuccessful())
                {
                    ErrorMessage error = ApiError.parseError(response);
//                    Toast.makeText(MyApplication.getInstance().getApplicationContext(), error.getErrors().get(0).getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("TAG", "Got error : " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void deleteProductFromCart(String api_token, int product_id) {
        EcommerceServices apiService = RestClient.getClient().create(EcommerceServices.class);
        Call<Object> call = apiService.deleteFromCart(product_id,api_token);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful())
                {
//                    Toast.makeText(MyApplication.getInstance().getApplicationContext(), "the product had deleted", Toast.LENGTH_LONG).show();
                }
                else if (!response.isSuccessful())
                {
                    Log.d("Error","Not succefull");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("TAG", "Got error : " + t.getLocalizedMessage());
            }
        });
    }
}
