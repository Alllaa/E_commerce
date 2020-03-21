package com.example.e_commerce.rest;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorMessage{

    @SerializedName("errors")
    private List<ErrorBody> errors;

    public List<ErrorBody> getErrors() {
        return errors;
    }


}
