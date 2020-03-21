package com.example.e_commerce.model;

import com.google.gson.annotations.SerializedName;

public class UserInfo {
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    private String password;
    @SerializedName("image")
    private String profilePic;
    @SerializedName("cover")
    private String coverPic;
    @SerializedName("api_token")
    private String api_token;
    @SerializedName("cart_count")
    private int cartCount;

    public UserInfo(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public int getCartCount() {
        return cartCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public String getApi_token() {
        return api_token;
    }
}