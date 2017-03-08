package com.example.fsaibene.appproductos.data.products.datasource.cloud;

import android.content.pm.LauncherApps;
import android.os.Handler;

import com.example.fsaibene.appproductos.data.api.ErrorResponse;
import com.example.fsaibene.appproductos.data.api.RestService;
import com.example.fsaibene.appproductos.products.products.domain.criteria.ProductCriteria;
import com.example.fsaibene.appproductos.products.products.domain.model.Product;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fsaibene on 8/2/2017.
 */

public class CloudProductsDataSource implements ICloudProductsDataSource{

    public static final String BASE_URL = "http://192.168.0.15/2/v1/";
    private final Retrofit mRetrofit;
    private final RestService mRestService;

    public CloudProductsDataSource() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mRestService = mRetrofit.create(RestService.class);
    }

    @Override
    public void getProducts(final ProductServiceCallback callback,
                            ProductCriteria criteria, String token) {

        Call<List<Product>> call = mRestService.getProducts(token);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call,
                                   Response<List<Product>> response) {
                // Procesamos los posibles casos
                processGetProductsResponse(response, callback);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
    private void processGetProductsResponse(Response<List<Product>> response,
                                            ProductServiceCallback callback) {
        // ¿LLegaron los productos sanos y salvos?
        if (response.isSuccessful()) {
            callback.onLoaded(response.body());
            return;
        }
        ResponseBody errorBody = response.errorBody();

        if (errorBody.contentType().subtype().equals("json")) {
            ErrorResponse errorResponse = ErrorResponse.fromErrorBody(errorBody);
            callback.onError(errorResponse.getMessage());
        }
        // Errores ajenos a la API
        callback.onError(response.code() + " " + response.message());

    }
}
