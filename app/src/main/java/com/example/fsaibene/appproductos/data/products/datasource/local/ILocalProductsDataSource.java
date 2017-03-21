package com.example.fsaibene.appproductos.data.products.datasource.local;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.fsaibene.appproductos.products.products.domain.criteria.ProductCriteria;
import com.example.fsaibene.appproductos.products.products.domain.model.Product;
import com.example.fsaibene.appproductos.selection.specification.ProviderSpecification;
import com.example.fsaibene.appproductos.selection.specification.Query;

import java.util.List;

/**
 * Created by fsaibene on 13/3/2017.
 */
public interface ILocalProductsDataSource {
    interface GetCallback {

        void onProductsLoaded(List<Product> products);

        void onDataNotAvailable(String error);
    }

    void get(@NonNull Query query, @NonNull GetCallback getCallback);

    void save(@NonNull Product product);

    void delete(@Nullable ProviderSpecification specification);
}
