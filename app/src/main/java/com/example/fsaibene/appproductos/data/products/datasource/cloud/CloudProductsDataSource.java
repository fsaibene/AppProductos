package com.example.fsaibene.appproductos.data.products.datasource.cloud;

import android.os.Handler;

import com.example.fsaibene.appproductos.products.products.domain.criteria.ProductCriteria;
import com.example.fsaibene.appproductos.products.products.domain.model.Product;
import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Created by fsaibene on 8/2/2017.
 */

public class CloudProductsDataSource implements ICloudProductsDataSource{

    private static HashMap<String, Product> API_DATA;
    private static final long LATENCY = 2000;
    static {
        API_DATA = new LinkedHashMap<>();
        for (int i = 0; i<25; i++){
            addProduct(i, "ProductoX " + (i + 1), "file:///android_asset/mock-product.png");
        }
    }
    private static void addProduct(float price, String name, String imageUrl){
        Product newProduct = new Product(price, name, imageUrl);
        API_DATA.put(newProduct.getCode(), newProduct);
    }
    @Override
    public void getProducts(final ProductsServiceCallback callback, ProductCriteria criteria) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onLoaded(Lists.newArrayList(API_DATA.values()));
            }
        }, LATENCY);

    }
}
