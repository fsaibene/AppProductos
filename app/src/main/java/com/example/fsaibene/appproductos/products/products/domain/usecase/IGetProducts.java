package com.example.fsaibene.appproductos.products.products.domain.usecase;

import android.support.annotation.NonNull;
import android.widget.GridLayout;

import com.example.fsaibene.appproductos.data.products.datasource.IProductsRepository;
import com.example.fsaibene.appproductos.products.products.domain.model.Product;
import com.example.fsaibene.appproductos.selection.specification.Query;
import com.example.fsaibene.appproductos.selection.specification.Specification;

import java.util.List;

/**
 * Created by fsaibene on 9/3/2017.
 */

public interface IGetProducts {
    void getProducts(@NonNull Query query, boolean forceLoad, GetProductsCallback callback);

    interface GetProductsCallback {
        void onSuccess(List<Product> products);

        void onError(String error);
    }
}
