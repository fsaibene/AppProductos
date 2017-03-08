package com.example.fsaibene.appproductos.data.api;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by fsaibene on 10/2/2017.
 */

public class ErrorResponse {
    @SerializedName("message")
    String mMessage;

    public String getMessage() {
        return mMessage;
    }

    public static ErrorResponse fromErrorBody(ResponseBody errorBody) {
        try {
            ErrorResponse errorResponse = new Gson()
                    .fromJson(errorBody.string(), ErrorResponse.class);

            return errorResponse;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ErrorResponse();
    }
}