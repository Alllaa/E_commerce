package com.example.e_commerce.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {

    @SerializedName("id")
    private String id;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("shipping_name")
    private String shipping_name;
    @SerializedName("payment_method")
    private String paymnet_method;
    @SerializedName("amount")
    private String amount;
    @SerializedName("shipping")
    private String shipping;
    @SerializedName("status")
    private String status;
    @SerializedName("address_id")
    private String address_id;
    @SerializedName("type")
    private String type;
    @SerializedName("expected_deliver_date")
    private String expectedDate;
    @SerializedName("human_status")
    private String human_status;
    @SerializedName("class_status")
    private String class_status;
    @SerializedName("total_amount")
    private String total_amount;
    @SerializedName("class_payment")
    private String class_payment;
    @SerializedName("products")
    private List<MyObject> product;
    @SerializedName("updated_at")
    private String updated;

    public String getUpdated() {
        return updated;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getShipping_name() {
        return shipping_name;
    }

    public String getPaymnet_method() {
        return paymnet_method;
    }

    public String getAmount() {
        return amount;
    }

    public String getShipping() {
        return shipping;
    }

    public String getStatus() {
        return status;
    }

    public String getAddress_id() {
        return address_id;
    }

    public String getType() {
        return type;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public String getHuman_status() {
        return human_status;
    }

    public String getClass_status() {
        return class_status;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public String getClass_payment() {
        return class_payment;
    }

    public List<MyObject> getProduct() {
        return product;
    }
    public class MyObject{

        @SerializedName("product")
        private Product product;

        public Product getProduct() {
            return product;
        }
    }
}
