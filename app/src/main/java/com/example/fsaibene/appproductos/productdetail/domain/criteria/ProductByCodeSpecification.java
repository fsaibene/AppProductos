package com.example.fsaibene.appproductos.productdetail.domain.criteria;

import android.net.Uri;

import com.example.fsaibene.appproductos.external.sqlite.DatabaseContract;
import com.example.fsaibene.appproductos.products.products.domain.model.Product;
import com.example.fsaibene.appproductos.selection.specification.MemorySpecification;
import com.example.fsaibene.appproductos.selection.specification.ProviderSpecification;
import com.google.common.base.Preconditions;

/**
 * Created by fsaibene on 10/3/2017.
 */

public class ProductByCodeSpecification implements MemorySpecification<Product>, ProviderSpecification{
    private String mProductCode;

        public ProductByCodeSpecification(String mProductCode) {
            this.mProductCode = Preconditions.checkNotNull(mProductCode, "productCode no puede ser null");
        }

        @Override public boolean isSatisfiedBy(Product product) {
            return mProductCode.equals(product.getCode());
        }

    @Override
    public Uri asProvider() {
        return DatabaseContract.Products.buildUriWith(mProductCode);
    }
}