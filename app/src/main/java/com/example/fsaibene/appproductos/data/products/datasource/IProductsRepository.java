package com.example.fsaibene.appproductos.data.products.datasource;

import com.example.fsaibene.appproductos.products.products.domain.criteria.ProductCriteria;
import com.example.fsaibene.appproductos.products.products.domain.model.Product;

import java.util.List;

/**
 * Created by fsaibene on 8/2/2017.
 */

public interface IProductsRepository {

    interface GetProductsCallback{
        void onProductsLoaded(List<Product> products);
        void onDataNotAvailable(String error);
    }

    void getProducts(GetProductsCallback callback, ProductCriteria criteria);
    void refreshProducts();
}
