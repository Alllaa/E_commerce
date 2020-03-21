package com.example.e_commerce.apiResponses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChangePasswordMessage {

    @SerializedName("message")
    private List<String> message;

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }
}
