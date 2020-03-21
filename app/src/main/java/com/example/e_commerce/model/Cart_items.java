package com.example.e_commerce.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cart_items implements Parcelable {

    @SerializedName("carts")
    private List<Cart_object> carts;
    @SerializedName("total_price")
    private int total_price;
    @SerializedName("shipping")
    private int shiping;
    @SerializedName("total_items")
    private int total_items;

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public void setShiping(int shiping) {
        this.shiping = shiping;
    }

    public void setTotal_items(int total_items) {
        this.total_items = total_items;
    }

    public List<Cart_object> getCarts() {
        return carts;
    }

    public int getTotal_price() {
        return total_price;
    }

    public int getShiping() {
        return shiping;
    }

    public int getTotal_items() {
        return total_items;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(total_price);
        parcel.writeInt(shiping);
        parcel.writeInt(total_items);
    }

    public class Cart_object{
        int x = 0;

        @SerializedName("id")
        private String id;
        @SerializedName("user_id")
        private  String user_id;

        @SerializedName("product")
        private Product product;

        @SerializedName("quantity")
        private String quantity;

        public String getQuantity() {
            return quantity;
        }
        public void setQuantity(String q) {
            this.quantity =q;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public Product getProduct() {
            return product;
        }
    }


    protected Cart_items(Parcel in) {
        total_price = in.readInt();
        shiping = in.readInt();
        total_items = in.readInt();
    }

    public static final Creator<Cart_items> CREATOR = new Creator<Cart_items>() {
        @Override
        public Cart_items createFromParcel(Parcel in) {
            return new Cart_items(in);
        }

        @Override
        public Cart_items[] newArray(int size) {
            return new Cart_items[size];
        }
    };

}
