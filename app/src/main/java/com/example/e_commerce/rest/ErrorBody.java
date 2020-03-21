package com.example.e_commerce.rest;

import com.google.gson.annotations.SerializedName;

public class ErrorBody {

    @SerializedName("code")
    private int code;

    @SerializedName("name")
    private String name;

    @SerializedName("message")
    private String message;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
