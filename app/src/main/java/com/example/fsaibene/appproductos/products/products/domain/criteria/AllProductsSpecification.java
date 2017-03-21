package com.example.fsaibene.appproductos.products.products.domain.criteria;

import android.net.Uri;

import com.example.fsaibene.appproductos.external.sqlite.DatabaseContract;
import com.example.fsaibene.appproductos.external.sqlite.DatabaseContract.Products;
import com.example.fsaibene.appproductos.products.products.domain.model.Product;
import com.example.fsaibene.appproductos.selection.specification.MemorySpecification;
import com.example.fsaibene.appproductos.selection.specification.ProviderSpecification;

/**
 * Created by fsaibene on 10/3/2017.
 */

public class AllProductsSpecification
        implements MemorySpecification<Product>,
        ProviderSpecification {
    @Override
    public boolean isSatisfiedBy(Product item) {
        return true;
    }

    @Override
    public Uri asProvider() {
        return Products.buildUri();
    }
}

