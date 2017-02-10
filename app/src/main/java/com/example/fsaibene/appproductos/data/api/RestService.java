package com.example.fsaibene.appproductos.data.api;

import com.example.fsaibene.appproductos.products.products.domain.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by fsaibene on 10/2/2017.
 */

public interface RestService {
    @GET("products")
    Call<List<Product>> getProducts();
}
