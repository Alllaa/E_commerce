package com.example.e_commerce.presenter;

import android.util.Log;

import com.example.e_commerce.model.GetDataFromServer;
import com.example.e_commerce.iEcommerce.IGetDataFromServer;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.presenter.interface_presnter.IShowProductPresenter;
import com.example.e_commerce.view.view_interface.IShowProduct;

public class ShowProductPresenter implements IShowProductPresenter {
   private IGetDataFromServer iGetProduct;
   private IShowProduct iShowProduct;

   Product oProduct;
   public ShowProductPresenter(IShowProduct newShowProdouct)
   {
       iShowProduct = newShowProdouct;
       iGetProduct = new GetDataFromServer();
   }

    @Override
    public void onSendData(String api_token,int num) {
        iGetProduct.getGetProductData(this,api_token,num);
    }

    @Override
    public void onAddToCart(String apiToken, int productId,String size_id, String color_id,int quantity) {
        iGetProduct.postProductToCart(this,apiToken,productId,color_id,size_id,quantity);
    }

    @Override
    public void onRateTheProduct(String api_token, int product_id, int rate) {
        iGetProduct.sendReview(api_token,product_id,rate);
    }

    @Override
    public void sendGetProduct(Product product) {
        oProduct = product;
        if(iShowProduct != null)
        {
            Log.d("gfg",product.getName_en()+"  "+product.getPrice());
            iShowProduct.onDataRecieved(product);
        }
    }

    @Override
    public void onAddToWish_List(String api_token, int product_id) {
        iGetProduct.addTofavourit(api_token,product_id);
    }

    @Override
    public void cancelCall() {
        iGetProduct.cancelCall();
    }
}
