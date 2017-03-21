package com.example.fsaibene.appproductos.data.api;

import com.example.fsaibene.appproductos.login.domain.entities.User;
import com.example.fsaibene.appproductos.products.products.domain.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by fsaibene on 10/2/2017.
 */

public interface RestService {

    String APP_PRODUCTOS_SERVICE_BASE_URL = "http://192.168.0.15/3/v1/";


    @GET("products")
    Call<List<Product>> getProducts(@Header("Authorization") String authorization);

    @POST("sessions")
    Call<User> login(@Header("Authorization") String authorization);

}
