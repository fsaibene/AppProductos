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

    public static final String BASE_URL = "http://192.168.0.15/api.appproducts.com/v1/";
    private final Retrofit mRetrofit;
    private final RestService mRestService;

    private static HashMap<String, Product> API_DATA;
//    private static final long LATENCY = 2000;
//    static {
//        API_DATA = new LinkedHashMap<>();
//        for (int i = 0; i<25; i++){
//            addProduct(i, "ProductoX " + (i + 1), "file:///android_asset/mock-product.png");
//        }
//    }

    public CloudProductsDataSource() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mRestService = mRetrofit.create(RestService.class);
    }

    private static void addProduct(float price, String name, String imageUrl){
        Product newProduct = new Product(price, name, imageUrl);
        API_DATA.put(newProduct.getCode(), newProduct);
    }
    @Override
    public void getProducts(final ProductsServiceCallback callback, ProductCriteria criteria) {
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                callback.onLoaded(Lists.newArrayList(API_DATA.values()));
//            }
//        }, LATENCY);

        Call<List<Product>> call = mRestService.getProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                // Procesamos los posibles casos
                processGetProductsResponse(response, callback);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
    private void processGetProductsResponse(Response<List<Product>> response, ProductsServiceCallback callback){
        String error = "Ha ocurrido un error";
        ResponseBody errorBody = response.errorBody();

        if (errorBody != null){
            if (errorBody.contentType().subtype().equals("json")){
                try {
                    ErrorResponse er = new Gson()
                            .fromJson(errorBody.toString(), ErrorResponse.class);
                    error = er.getMessage();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            callback.onError(error);
        }
        if (response.isSuccessful()) {
            callback.onLoaded(response.body());
        }
    }
}
