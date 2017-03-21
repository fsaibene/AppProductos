package com.example.fsaibene.appproductos.data.products.datasource.cloud;

import android.support.annotation.NonNull;

import com.example.fsaibene.appproductos.products.products.domain.criteria.ProductCriteria;
import com.example.fsaibene.appproductos.products.products.domain.model.Product;
import com.example.fsaibene.appproductos.selection.specification.Query;
import com.example.fsaibene.appproductos.selection.specification.Specification;

import java.util.List;

/**
 * Created by fsaibene on 8/2/2017.
 */

public interface ICloudProductsDataSource {
    interface ProductServiceCallback{
        void onLoaded(List<Product> products);
        void onError(String error);
    }
    void getProducts(@NonNull Query query, String userToken, ProductServiceCallback callback);
}
