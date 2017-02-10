package com.example.fsaibene.appproductos.data.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fsaibene on 10/2/2017.
 */

public class ErrorResponse {
    @SerializedName("message")
    String mMessage;

    public String getMessage() {
        return mMessage;
    }
}
