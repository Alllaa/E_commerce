package com.example.e_commerce.rest;

import android.util.Log;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ApiError {

        public static ErrorMessage parseError(Response<?> response) {
            Converter<ResponseBody, ErrorMessage> converter =
                    RestClient.getClient()
                            .responseBodyConverter(ErrorMessage.class, new Annotation[0]);

            ErrorMessage error;

            try {
                error = converter.convert(response.errorBody());
            } catch (IOException e) {
                return new ErrorMessage();
            }

            return error;
        }

}
