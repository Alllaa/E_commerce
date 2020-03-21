package com.example.e_commerce.model;

import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.example.e_commerce.MyApplication;
import com.example.e_commerce.apiResponses.Message;
import com.example.e_commerce.iEcommerce.IGetDataFromServer;
import com.example.e_commerce.presenter.interface_presnter.IArrivalSellerPresenter;
import com.example.e_commerce.presenter.interface_presnter.ICategoriesPresenter;
import com.example.e_commerce.presenter.interface_presnter.IDealsPresenter;
import com.example.e_commerce.presenter.interface_presnter.IHeadLinePresenter;
import com.example.e_commerce.presenter.interface_presnter.IHomePresenter;
import com.example.e_commerce.presenter.interface_presnter.IShowProductPresenter;
import com.example.e_commerce.rest.ApiError;
import com.example.e_commerce.rest.EcommerceServices;
import com.example.e_commerce.rest.ErrorMessage;
import com.example.e_commerce.rest.RestClient;
import com.example.e_commerce.view.HomeActivity;
import com.example.e_commerce.view.MainActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetDataFromServer implements IGetDataFromServer {
    //getProductData
    ItemList itemList;
    Item item;

    //getCategoriesData
    List<TopCategories> topCategoriesList = new ArrayList<>();

    //GetDataDeals
    List<TopCategories> ads;
    List<TopCategories> topCategoriesList2;
    List<TopCategories> topBrandslist;

    //GetHomeData
    List<NewArrivals> newArrivalsList;
    List<TopCategories> topCategoriesList3;
    List<BestSeller> bestSellersList;

    //GetBestSellerData
    List<BestSeller> bestSellersList2;

    //GetBestSellerData
    List<NewArrivals> getNewArrivalsData;

    //Product
    ItemList pr;
    Product product;

    //Favourite
    List<Product> productList;

    private Call mCall;
    @Override
    public void getProductsData(final ICategoriesPresenter getList, int num, String api_token) {

        final EcommerceServices apiService;
        apiService = RestClient.getClient().create(EcommerceServices.class);

        Call<ItemList> call = apiService.getAllProduct(num, api_token);

        mCall = call;
        mCall.enqueue(new Callback<ItemList>() {
            @Override
            public void onResponse(Call<ItemList> call, Response<ItemList> response) {
                if (!response.isSuccessful()) {
                    Log.d("Code Product:", "Error in response");
                    return;
                }
                itemList = response.body();
                item = response.body().getItem();
                item = itemList.getItem();
                getList.sendcategories(item.getData());
            }

            @Override
            public void onFailure(Call<ItemList> call, Throwable t) {
                Log.d("onFailure product:", "Failure  in response");
                Log.d("onFailure product:", t.getMessage());
            }
        });
    }


    @Override
    public void getCategoriesData(final IHeadLinePresenter getList) {
        final EcommerceServices apiService;
        apiService = RestClient.getClient().create(EcommerceServices.class);

        Call<List<TopCategories>> call = apiService.getTopCategories();
        mCall = call;
        mCall.enqueue(new Callback<List<TopCategories>>() {
            @Override
            public void onResponse(Call<List<TopCategories>> call, Response<List<TopCategories>> response) {
                if (!response.isSuccessful()) {
                    Log.d("Code HeadLine:", "Error in response");
                    return;
                }

                topCategoriesList = response.body();
                Log.d("Data", topCategoriesList.get(1).getName_en());
                getList.sendCategories(topCategoriesList);
            }

            @Override
            public void onFailure(Call<List<TopCategories>> call, Throwable t) {
                Log.d("onFailure HeadLines:", "Failure  in response");
                if (t != null)
                    Log.d("onFailure HeadLines", t.getMessage());
            }
        });

    }


    @Override
    public void getDealsData(final IDealsPresenter getList) {

        final EcommerceServices apiService;
        apiService = RestClient.getClient().create(EcommerceServices.class);

        Call<ListDataFromServer> call = apiService.getDeals();

        mCall = call;
        mCall.enqueue(new Callback<ListDataFromServer>() {
            @Override
            public void onResponse(Call<ListDataFromServer> call, Response<ListDataFromServer> response) {
                if (!response.isSuccessful()) {
                    Log.d("Code Deals:", "Error in response");
                    return;
                }
                ads = response.body().getAds();
                topCategoriesList2 = response.body().getTop_categories();
                topBrandslist = response.body().getTopBrand();
                getList.sendDeals(ads, topCategoriesList2, topBrandslist);
            }

            @Override
            public void onFailure(Call<ListDataFromServer> call, Throwable t) {

                Log.d("onFailure GetDeals:", "Failure  in response");

            }
        });
    }

    @Override
    public void getHomeData(final IHomePresenter getList, String api_token) {
        final EcommerceServices apiService;
        apiService = RestClient.getClient().create(EcommerceServices.class);

        Call<ListDataFromServer> call = apiService.getHome(api_token);
        mCall = call;
        mCall.enqueue(new Callback<ListDataFromServer>() {

            @Override
            public void onResponse(Call<ListDataFromServer> call, Response<ListDataFromServer> response) {
                if (!response.isSuccessful()) {
                    Log.d("Code:", "Error in response");
                    return;
                }
                Log.d("TAG", "Total number of Movies fetched : " + response.body().getItems().size());
                newArrivalsList = response.body().getItems();
                topCategoriesList3 = response.body().getTop_categories();
                bestSellersList = response.body().getBest_seller();
                getList.sendHome(newArrivalsList, topCategoriesList3, bestSellersList);
            }

            @Override
            public void onFailure(Call<ListDataFromServer> call, Throwable t) {
                Log.d("onFailure:", "Failure  in response");
                // Toast.makeText(AppClass.getObject(), "Internet is slow", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void addTofavourit( String api_token, int product_id) {

        final EcommerceServices apiService;
        apiService = RestClient.getClient().create(EcommerceServices.class);


        Call<Message> call = apiService.addToWishList(api_token, product_id);
        call.enqueue(new Callback<Message>() {

            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful())
                {
                    //Toast.makeText(MyApplication.getInstance().getApplicationContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                    //Snackbar.make(HomeActivity.coordinatorLayout,response.body().getMessage(), BaseTransientBottomBar.LENGTH_SHORT).show();
                }
                else if (!response.isSuccessful())
                {
                    ErrorMessage error = ApiError.parseError(response);
                    Toast.makeText(MyApplication.getInstance().getApplicationContext(), error.getErrors().get(0).getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.d("onFailure:", "Failure  in response");
            }
        });
    }

    @Override
    public void getBestSellerData(final IArrivalSellerPresenter getList, String api_token) {
        final EcommerceServices apiService;
        apiService = RestClient.getClient().create(EcommerceServices.class);

        Call<ItemList> call = apiService.getBestSeller(api_token);
        mCall = call;
        mCall.enqueue(new Callback<ItemList>() {
            @Override
            public void onResponse(Call<ItemList> call, Response<ItemList> response) {
                if (!response.isSuccessful()) {
                    Log.d("Code:", "Error in response");
                    return;
                }
                bestSellersList2 = response.body().getBestSeller();
                getList.sendBestSeller(bestSellersList2);
            }

            @Override
            public void onFailure(Call<ItemList> call, Throwable t) {
                Log.d("onFailure:", "Failure  in response");
            }
        });

    }

    @Override
    public void getNewArrivalsData(final IArrivalSellerPresenter getList,String api_token) {
        final EcommerceServices apiService;
        apiService = RestClient.getClient().create(EcommerceServices.class);

        Call<ListDataFromServer> call = apiService.getNewArrivals(api_token);
        mCall = call;
        mCall.enqueue(new Callback<ListDataFromServer>() {
            @Override
            public void onResponse(Call<ListDataFromServer> call, Response<ListDataFromServer> response) {
                if (!response.isSuccessful()) {
                    Log.d("Code:", "Error in response");
                    return;
                }
                getNewArrivalsData = response.body().getNewArrival();
                getList.sendNewArrivals(getNewArrivalsData);
            }

            @Override
            public void onFailure(Call<ListDataFromServer> call, Throwable t) {
                Log.d("onFailure:", "Failure  in response");
            }
        });
    }

    @Override
    public void getGetProductData(final IShowProductPresenter getList, String api_token, int num) {
        final EcommerceServices apiService;
        apiService = RestClient.getClient().create(EcommerceServices.class);

        Call<Product> call = apiService.getProduct(num,api_token);
        mCall = call;
        mCall.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (!response.isSuccessful()) {
                    Log.d("Code:", "Error in response Whyyyyyyyyyyyyyy");
                    return;
                }
//                    pr = response.body();
                product = response.body();
//                    product = pr.get_product();
                Log.d("Product", product.getName_en());
                getList.sendGetProduct(product);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.d("onFailure:", "Failure  in response");
                Log.d("onFailure:", t.getMessage());
            }
        });
    }

    @Override
    public void postProductToCart(IShowProductPresenter getList, String api_token, int productId, String color_id, String size_id, int quantity) {
        final EcommerceServices apiService;
        apiService = RestClient.getClient().create(EcommerceServices.class);


        Call<Object> call = apiService.addToCart(api_token,productId,1,size_id,color_id);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful())
                {
                    //Toast.makeText(MyApplication.getInstance().getApplicationContext(), "the product is added to cart", Toast.LENGTH_LONG).show();
                    //Snackbar.make(HomeActivity.coordinatorLayout,"product added to cart, BaseTransientBottomBar.LENGTH_SHORT).show();

                }
                else if (!response.isSuccessful())
                {
                    ErrorMessage error = ApiError.parseError(response);
                    Toast.makeText(MyApplication.getInstance().getApplicationContext(), error.getErrors().get(0).getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("onFailure:", "Failure  in response");
            }
        });
    }

    @Override
    public void getMyFavouriteFromSever(final GetFavourite getList, String api_token) {
        final EcommerceServices apiService;
        apiService = RestClient.getClient().create(EcommerceServices.class);

        Call<ProductList> call = apiService.getMyFavourite(api_token);
        mCall = call;
        mCall.enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                if (!response.isSuccessful())
                {
                    Log.d("Code:", "Error in response");
                }
                productList = response.body().getFavourite();
              //  Log.d("Favourite",productList.get(0).getName_en());
                getList.sendFavourtieProduct(productList);
            }

            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {
                Log.d("onFailure:", "Failure  in response");
            }
        });
    }

    @Override
    public void deleteFromFavourite(GetFavourite getList, String api_token, int product_id) {
        final EcommerceServices apiService;
        apiService = RestClient.getClient().create(EcommerceServices.class);

        Call<Message>call = apiService.addToWishList(api_token,product_id);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful())
                {
                    //Toast.makeText(MyApplication.getInstance().getApplicationContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                }
                else if (!response.isSuccessful())
                {
                    ErrorMessage error = ApiError.parseError(response);
                    Toast.makeText(MyApplication.getInstance().getApplicationContext(), error.getErrors().get(0).getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.d("onFailure:", "Failure  in response");
            }
        });
    }

    @Override
    public void buy(GetFavourite getList, String api_token, int productId) {
        final EcommerceServices apiService;
        apiService = RestClient.getClient().create(EcommerceServices.class);

        Call<Object> call = apiService.addToCart(api_token,productId,1,null,null);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful())
                {
                    //Toast.makeText(MyApplication.getInstance().getApplicationContext(), "the product is added to cart", Toast.LENGTH_LONG).show();
                    //Snackbar.make(HomeActivity.coordinatorLayout,"product added to cart, BaseTransientBottomBar.LENGTH_SHORT).show();

                }
                else if (!response.isSuccessful())
                {
                    ErrorMessage error = ApiError.parseError(response);
                    Toast.makeText(MyApplication.getInstance().getApplicationContext(), error.getErrors().get(0).getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("onFailure:", "Failure  in response");
            }
        });
    }

    @Override
    public void getFroSearch(String api_token, String key, final ICategoriesPresenter getList) {
        final EcommerceServices apiService;
        apiService = RestClient.getClient().create(EcommerceServices.class);

        Call<ItemList> call = apiService.getForSearch( key,api_token);

        mCall = call;
        mCall.enqueue(new Callback<ItemList>() {
            @Override
            public void onResponse(Call<ItemList> call, Response<ItemList> response) {
                if (!response.isSuccessful()) {
                    Log.d("Code Product:", "Error in response");
                    return;
                }
                itemList = response.body();
                item = response.body().getItem();
                item = itemList.getItem();
                getList.sendcategories(item.getData());
            }

            @Override
            public void onFailure(Call<ItemList> call, Throwable t) {
                Log.d("onFailure product:", "Failure  in response");
                Log.d("onFailure product:", t.getMessage());
                  }
        });
    }

    public void sendReview(String api_token, int product, final int     rate) {
        final EcommerceServices apiService;
        apiService = RestClient.getClient().create(EcommerceServices.class);

        Call call = apiService.rateProduct(api_token, product,rate);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful())
                {
                    Snackbar.make(HomeActivity.coordinatorLayout,"you give "+rate+" stars", BaseTransientBottomBar.LENGTH_SHORT)
                            .setTextColor(Color.GREEN).show();
                }

                else if (!response.isSuccessful())
                {
                    ErrorMessage error = ApiError.parseError(response);
                    Toast.makeText(MyApplication.getInstance().getApplicationContext(), error.getErrors().get(0).getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("onFailure:", "Failure  in rating");
            }
        });
    }

    @Override
    public void cancelCall() {
        if(mCall != null){
            mCall.cancel();
            Log.d("rrr","CANCELED");

        }
    }
}
