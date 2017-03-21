package com.example.fsaibene.appproductos.data.products.datasource.cloud;

import android.content.pm.LauncherApps;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.fsaibene.appproductos.data.api.ErrorResponse;
import com.example.fsaibene.appproductos.data.api.RestService;
import com.example.fsaibene.appproductos.products.products.domain.criteria.ProductCriteria;
import com.example.fsaibene.appproductos.products.products.domain.model.Product;
import com.example.fsaibene.appproductos.selection.specification.Query;
import com.example.fsaibene.appproductos.selection.specification.Specification;
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

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by fsaibene on 8/2/2017.
 */

public class CloudProductsDataSource implements ICloudProductsDataSource{
    private static CloudProductsDataSource INSTANCE = null;

    private final Retrofit mRetrofit;
    private final RestService mRestService;

    private CloudProductsDataSource() {

        mRetrofit = new Retrofit.Builder()
                .baseUrl(RestService.APP_PRODUCTOS_SERVICE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mRestService = mRetrofit.create(RestService.class);
    }

    public static CloudProductsDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CloudProductsDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getProducts(@NonNull Query query, String userToken,
                            final ProductServiceCallback callback) {
        checkNotNull(query, "query no puede ser null");

        Call<List<Product>> call = mRestService.getProducts(userToken);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call,
                                   Response<List<Product>> response) {

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

        if (response.isSuccessful()) {
            List<Product> serverProducts = response.body();

            if (serverProducts.size() == 0) {
                callback.onError("No hay productos en el servidor");
                return;
            }

            callback.onLoaded(serverProducts);
            return;
        }

        ResponseBody errorBody = response.errorBody();

        if (errorBody.contentType().subtype().equals("json")) {
            ErrorResponse errorResponse = ErrorResponse.fromErrorBody(errorBody);
            callback.onError(errorResponse.getMessage());
            return;
        }

        // Errores ajenos a la API
        callback.onError(response.code() + " " + response.message());
    }
}
