package com.example.e_commerce.rest;

import com.example.e_commerce.apiResponses.ChangePasswordMessage;
import com.example.e_commerce.apiResponses.Message;
import com.example.e_commerce.apiResponses.ResetCodeMessage;
import com.example.e_commerce.model.Address;
import com.example.e_commerce.model.Cart_items;
import com.example.e_commerce.model.ItemList;
import com.example.e_commerce.model.ListDataFromServer;
import com.example.e_commerce.model.Order;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.model.ProductList;
import com.example.e_commerce.model.TopCategories;
import com.example.e_commerce.model.User;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EcommerceServices {


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("api/v1/user/auth/signup")
    Call<User> signUpUser(@Field("name")String Name,
                          @Field("email")String Email,
                          @Field("phone")String Phone,
                          @Field("password")String Password
                          //@Body User user
    );



    @FormUrlEncoded
    @POST("api/v1/user/auth/signin")
    Call<User> signInUser(@Field("name")String Name,
                          @Field("password")String Password,
                          @Field("mobile_token")String mopileToken,
                          @Field("os")String os
                          //@Body User user
    );

    @FormUrlEncoded
    @POST("api/v1/user/auth/send-reset-password-code")
    Call<ResetCodeMessage> sendResetCode(@Field("phone")String phone);

    @PATCH("api/v1/user/auth/reset-password")
    Call<User> resetPassword(@Query("phone") String phone,
                             @Query("reset_password_code")int code,
                             @Query("password")String password

    );

    @GET("api/v1/user/auth/get-profile")
    Call<User> getProgile(@Query("api_token")String api_token);

    @PATCH("api/v1/user/auth/update-password")
    Call<ChangePasswordMessage> updatePassword(@Query("api_token") String api_token,
                                               @Query("old_password") String oldPassword,
                                               @Query("new_password") String newPasswprd);


    @POST("api/v1/user/app/contact-us")
    Call<Object> contactUs(@Query("name")String name,
                         @Query("email")String email,
                         @Query("message")String message
                         );

    @Multipart
    @POST("api/v1/user/auth/update-profile")
    Call<ResponseBody> updateUser(@Part("api_token") RequestBody api_token,
                          @Part("name")RequestBody  name,
                          @Part("email")RequestBody  email,
                                  @Part MultipartBody.Part file
    );

    @GET("api/v1/user/app/address")
    Call<List<Address>> getAddresses(@Query("api_token")String api_token);

    @POST("api/v1/user/app/address")
    Call<Object> createAddress(@Body Address address,
                             @Query("api_token")String api_token
                             );

    @GET("api/v1/user/app/home")
    Call<ListDataFromServer> getHome(
            @Query("api_token")String api_token
    );

    @GET("api/v1/user/app/deals-page")
    Call<ListDataFromServer> getDeals();

    @GET("api/v1/user/app/categories")
    Call<List<TopCategories>> getTopCategories();

    @GET("api/v1/user/app/products")
    Call<ItemList> getAllProduct(
            @Query("category_id") int category_id,
            @Query("api_token")String api_token
    );

    @GET("api/v1/user/app/new-arrival")
    Call<ListDataFromServer> getNewArrivals(
            @Query("api_token")String api_tpken
    );
    @GET("api/v1/user/app/best-seller")
    Call<ItemList> getBestSeller(
            @Query("api_token")String api_tpken
    );

    @GET("api/v1/user/app/product/{id}")
    Call<Product> getProduct(
            @Path("id") int id,
            @Query("api_token")String api_token

    );

    @Headers("Accept: application/json")
    @POST("api/v1/user/app/cart/add")
    Call<Object> addToCart(
            @Query("api_token") String api_token,
            @Query("product_id") int id,
            @Query("quantity") int quatity,
            @Query("size_id") String size_id,
            @Query("color_id") String color_id
    );
    @Headers("Accept: application/json")
    @POST("api/v1/user/app/action/favourite")
    Call<Message> addToWishList(
            @Query("api_token") String api_token,
            @Query("product_id") int id
    );

    @GET("api/v1/user/auth/favourite")
    Call<ProductList>getMyFavourite(
            @Query("api_token")String api_token
    );

    @GET("api/v1/user/app/order")
    Call<List<Order>> getOrders(@Query("api_token")String api_token);

    @GET("api/v1/user/app/cart/get-carts")
    Call<Cart_items> getCart(@Query("api_token")String api_token);


    @PATCH("/api/v1/user/app/cart/update-cart")
    Call<Object> updateQuantity(
            @Query("api_token")String api_token,
            @Query("quantity")int quantity,
            @Query("cart_id") int cart_id
    );
    @DELETE("api/v1/user/app/cart/delete/{product_id}")

    Call<Object> deleteFromCart(
            @Path("product_id") int product_id,
            @Query("api_token") String api_token
    );

    @POST("api/v1/user/app/order")
    Call<Void> createOrder(@Query("api_token")String api_token,
                     @Query("address_id")String address_id,
                     @Query("payment_method")String p
                     );

    @GET("api/v1/user/app/products")
    Call<ItemList> getForSearch(
            @Query("keyword") String Key,
            @Query("api_token")String api_token
    );

  @POST("api/v1/user/app/action/review")
    Call<Void> rateProduct(
            @Query("api_token")String api_token,
            @Query("product_id")int id,
            @Query("rate")int rate
    );


}
