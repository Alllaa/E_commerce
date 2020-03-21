package com.example.e_commerce.model;

import android.graphics.Color;
import android.util.Log;

import com.example.e_commerce.apiResponses.ChangePasswordMessage;
import com.example.e_commerce.apiResponses.ResetCodeMessage;
import com.example.e_commerce.iEcommerce.Iinteractor;
import com.example.e_commerce.presenter.interface_presnter.IResetPasswordPresenter;
import com.example.e_commerce.presenter.interface_presnter.ISendResetPasswordCodePresenter;
import com.example.e_commerce.presenter.interface_presnter.ISignInPresenter;
import com.example.e_commerce.presenter.interface_presnter.ISignUpPresenter;
import com.example.e_commerce.presenter.interface_presnter.IUpdateProfilePresenter;
import com.example.e_commerce.presenter.interface_presnter.ImyAccountPresenter;
import com.example.e_commerce.rest.ApiError;
import com.example.e_commerce.rest.EcommerceServices;
import com.example.e_commerce.rest.ErrorBody;
import com.example.e_commerce.rest.ErrorMessage;
import com.example.e_commerce.rest.RestClient;
import com.example.e_commerce.view.HomeActivity;
import com.example.e_commerce.view.MainActivity;
import com.example.e_commerce.view.view_interface.ImainActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInteractor implements Iinteractor {


    @Override
    public void signUpUser(UserInfo user, final ISignUpPresenter presenter) {

        EcommerceServices apiService = RestClient.getClient().create(EcommerceServices.class);
        Call<User> calling = apiService.signUpUser(user.getName(), user.getEmail(), user.getPhone(), user.getPassword());

        calling.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    presenter.isSignedIn(false, null);
                    presenter.hideProgressbar();
                    Log.d("TAG", "ERROR :  " + response.raw().body().toString());
                    ErrorMessage error = ApiError.parseError(response);
                    String errorMessage = "";
                    for (ErrorBody message : error.getErrors()) {
                        Log.d("TAG", message.getName() + " : " + message.getMessage());
                        errorMessage += message.getMessage() + "\n";
                    }

                    errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
                    Snackbar.make(MainActivity.coordinatorLayout, errorMessage, BaseTransientBottomBar.LENGTH_LONG).setDuration(5000)
                            .setBackgroundTint(Color.rgb(200,0,0)).setTextColor(Color.rgb(255, 255, 255)).show();
                } else if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    Log.d("User", user.getUser().getName() + "  " + user.getUser().getApi_token() + "  " + user.getUser().getPhone() + "  " + user.getUser().getEmail() + "  ");
                    presenter.isSignedIn(true, user.getUser());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                presenter.isSignedIn(false, null);
                Log.e("TAG", "Got error : " + t.getLocalizedMessage());
                if (t.getLocalizedMessage().contains("timeout")) {
                    //Toast.makeText(MyApplication.getInstance().getApplicationContext(),"Time error... try again later",Toast.LENGTH_SHORT).show();
                    Snackbar.make(MainActivity.coordinatorLayout, "Connection error... try later", BaseTransientBottomBar.LENGTH_LONG).setBackgroundTint(Color.rgb(200,0,0)).show();
                }
            }
        });
    }

    @Override
    public void signInUser(UserInfo user, final ISignInPresenter presenter) {
        EcommerceServices apiService = RestClient.getClient().create(EcommerceServices.class);
        Call<User> calling = apiService.signInUser(user.getName(), user.getPassword(), "121212121", "android");

        calling.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    presenter.isSignedIn(false, null);
                    presenter.hideProgressbar();
                    Log.d("TAG", "ERROR :  " + response.raw().body().toString());
                    ErrorMessage error = ApiError.parseError(response);
                    String errorMessage = "";
                    for (ErrorBody message : error.getErrors()) {
                        Log.d("TAG", message.getName() + " : " + message.getMessage());
                        errorMessage += message.getMessage() + "\n";
                    }
                    errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
                    Snackbar.make(MainActivity.coordinatorLayout, errorMessage, BaseTransientBottomBar.LENGTH_LONG).setDuration(5000)
                            .setBackgroundTint(Color.rgb(200,0,0)).setTextColor(Color.rgb(255, 255, 255)).show();
                    //Toast.makeText(MyApplication.getInstance().getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();
                } else if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    Log.d("User", user.getUser().getName() + "  " + user.getUser().getApi_token() + "  " + user.getUser().getPhone() + "  " + user.getUser().getEmail() + "  ");
                    presenter.isSignedIn(true, user.getUser());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                presenter.isSignedIn(false, null);
                if (t.getLocalizedMessage().contains("timeout")) {
                    //    Toast.makeText(MyApplication.getInstance().getApplicationContext(),"Time error... try again later",Toast.LENGTH_SHORT).show();
                    Snackbar.make(MainActivity.coordinatorLayout, "Connection error... try later", BaseTransientBottomBar.LENGTH_LONG).setBackgroundTint(Color.rgb(200,0,0)).show();
                }
                Log.e("TAG", "Got error : " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void sendResetPasswordCode(final ISendResetPasswordCodePresenter presenter, final String Phone) {

        EcommerceServices apiService = RestClient.getClient().create(EcommerceServices.class);
        Call<ResetCodeMessage> calling = apiService.sendResetCode(Phone);

        calling.enqueue(new Callback<ResetCodeMessage>() {
            @Override
            public void onResponse(Call<ResetCodeMessage> call, Response<ResetCodeMessage> response) {
                if (!response.isSuccessful()) {
                    Log.d("TAG", "ERROR :  " + response.raw().body().toString());
                    ErrorMessage error = ApiError.parseError(response);
                    String errorMessage = "";
                    for (ErrorBody message : error.getErrors()) {
                        Log.d("TAG", message.getName() + " : " + message.getMessage());
                        errorMessage += message.getMessage() + "\n";
                    }
//                    Toast.makeText(MyApplication.getInstance().getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();
                    Snackbar.make(MainActivity.coordinatorLayout, errorMessage, BaseTransientBottomBar.LENGTH_LONG).setBackgroundTint(Color.rgb(200,0,0)).show();
                } else if (response.isSuccessful() && response.body() != null) {
                    Log.d("asas", response.body().getMessage() + " : " + response.body().getCode());
                    presenter.getCodeandPhone(response.body().getCode(), Phone);

                }
            }

            @Override
            public void onFailure(Call<ResetCodeMessage> call, Throwable t) {
                Log.e("TAG", "Got error : " + t.getLocalizedMessage());
                if (t.getLocalizedMessage().contains("timeout")) {
//                    Toast.makeText(MyApplication.getInstance().getApplicationContext(),"Time error... try again later",Toast.LENGTH_SHORT).show();
                    Snackbar.make(MainActivity.coordinatorLayout, "Connection error... try later", BaseTransientBottomBar.LENGTH_LONG).setBackgroundTint(Color.rgb(200,0,0)).show();
                }
            }
        });
    }

    @Override
    public void resetPsssword(String phone, int ressetcode, String newPassword, final IResetPasswordPresenter presenter) {
        EcommerceServices apiService = RestClient.getClient().create(EcommerceServices.class);
        Call<User> calling = apiService.resetPassword(phone, ressetcode, newPassword);

        calling.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    presenter.isSignedIn(false, "");
                } else if (response.isSuccessful()) {
                    User user = response.body();
                    Log.d("User", user.getUser().getName() + "  " + user.getUser().getApi_token() + "  " + user.getUser().getPhone() + "  " + user.getUser().getEmail() + "  ");
                    presenter.isSignedIn(true, user.getUser().getApi_token());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("TAG", "Got error : " + t.getLocalizedMessage());
                Snackbar.make(MainActivity.coordinatorLayout, "Error happened... try later", BaseTransientBottomBar.LENGTH_LONG).setBackgroundTint(Color.rgb(200,0,0)).show();
                presenter.isSignedIn(false, "");
            }
        });
    }

    @Override
    public void getProfile(String api_token, final ImyAccountPresenter presenter) {
        EcommerceServices apiService = RestClient.getClient().create(EcommerceServices.class);
        Call<User> calling = apiService.getProgile(api_token);

        calling.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {

                } else if (response.isSuccessful()) {
                    User user = response.body();
                    presenter.getUser(user);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("TAG", "Got error : " + t.getLocalizedMessage());
                if (t.getLocalizedMessage().contains("timeout")) {
//                    Toast.makeText(MyApplication.getInstance().getApplicationContext(),"Time error... try again later",Toast.LENGTH_SHORT).show();
                    Snackbar.make(HomeActivity.coordinatorLayout, "Connection error... try later", BaseTransientBottomBar.LENGTH_LONG).setBackgroundTint(Color.rgb(200,0,0)).show();
                }
            }
        });
    }

    @Override
    public void updatePassword(String oldPassword, String newPassword, String api_token) {
        EcommerceServices apiService = RestClient.getClient().create(EcommerceServices.class);
        Call<ChangePasswordMessage> calling = apiService.updatePassword(api_token, oldPassword, newPassword);

        calling.enqueue(new Callback<ChangePasswordMessage>() {
            @Override
            public void onResponse(Call<ChangePasswordMessage> call, Response<ChangePasswordMessage> response) {
                if (!response.isSuccessful()) {
                    ErrorMessage error = ApiError.parseError(response);
                    String errorMessage = "";
                    for (ErrorBody message : error.getErrors()) {
                        Log.d("TAG", message.getName() + " : " + message.getMessage());
                        errorMessage += message.getMessage() + "\n";
                    }
                    errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
//                    Toast.makeText(MyApplication.getInstance().getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();
                    Snackbar.make(HomeActivity.coordinatorLayout, errorMessage, BaseTransientBottomBar.LENGTH_LONG).setBackgroundTint(Color.rgb(200,0,0)).show();
                } else if (response.isSuccessful()) {
                    ChangePasswordMessage c = response.body();
                    Log.d("adde", c.getMessage().get(0) + "");
                    //Toast.makeText(MyApplication.getInstance().getApplicationContext(),c.getMessage().get(0),Toast.LENGTH_SHORT).show();
                    Snackbar.make(HomeActivity.coordinatorLayout, c.getMessage().get(0), BaseTransientBottomBar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordMessage> call, Throwable t) {
                Log.e("TAG", "Got error : " + t.getLocalizedMessage());
                if (t.getLocalizedMessage().contains("Failed to connect") || t.getLocalizedMessage().contains("timeout")) {
                    //Toast.makeText(MyApplication.getInstance().getApplicationContext(),"Connection error... try later",Toast.LENGTH_LONG).show();
                    Snackbar.make(HomeActivity.coordinatorLayout, "Connection error... try later", BaseTransientBottomBar.LENGTH_LONG).setBackgroundTint(Color.rgb(200,0,0)).show();
                }
            }
        });
    }

    @Override
    public void updateProfile(String api_token, String name, String email, String path, final IUpdateProfilePresenter presenter) {
        //Log.d("rtrt",path);
        MultipartBody.Part body = null;
        if(path != null) {
            File file = new File(path);
            RequestBody fbody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body = MultipartBody.Part.createFormData("image", file.getName(), fbody);
        }
        body = path == null ? null : body;

        RequestBody fullName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody Email = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody Api_token = RequestBody.create(MediaType.parse("text/plain"), api_token);

        EcommerceServices apiService = RestClient.getClient().create(EcommerceServices.class);
        Call<ResponseBody> calling = apiService.updateUser(Api_token, fullName, Email, body);

        calling.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    ErrorMessage error = ApiError.parseError(response);
                    String errorMessage = "";
                    for (ErrorBody message : error.getErrors()) {
                        Log.d("TAG", message.getName() + " : " + message.getMessage());
                        errorMessage += message.getMessage() + "\n";
                    }
                    errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
                    //Toast.makeText(MyApplication.getInstance().getApplicationContext(),errorMessage,Toast.LENGTH_LONG).show();
                    Snackbar.make(HomeActivity.coordinatorLayout, errorMessage, BaseTransientBottomBar.LENGTH_LONG).setBackgroundTint(Color.rgb(200,0,0)).show();

                } else if (response.isSuccessful()) {
                    //User c = response.body();
                    Log.d("adde", "oooo");
                    presenter.nav();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "Got error : " + t.getMessage());
                if (t.getMessage() != null && t.getMessage().contains("Failed to connect")) {
//                    Toast.makeText(MyApplication.getInstance().getApplicationContext(),"Connection error... try later",Toast.LENGTH_LONG).show();
                    Snackbar.make(HomeActivity.coordinatorLayout, "Connection error... try later", BaseTransientBottomBar.LENGTH_LONG).setBackgroundTint(Color.rgb(200,0,0)).show();

                }
            }
        });

    }
}