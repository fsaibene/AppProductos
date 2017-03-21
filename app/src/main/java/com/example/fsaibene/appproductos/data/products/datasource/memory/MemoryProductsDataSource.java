package com.example.fsaibene.appproductos.data.products.datasource.memory;

import android.support.annotation.NonNull;

import com.example.fsaibene.appproductos.productdetail.domain.criteria.ProductsSelector;
import com.example.fsaibene.appproductos.products.products.domain.criteria.ProductCriteria;
import com.example.fsaibene.appproductos.products.products.domain.model.Product;
import com.example.fsaibene.appproductos.selection.specification.MemorySpecification;
import com.example.fsaibene.appproductos.selection.specification.Query;
import com.example.fsaibene.appproductos.selection.specification.Specification;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by fsaibene on 8/2/2017.
 */
public class MemoryProductsDataSource implements IMemoryProductsDataSource{
    private static MemoryProductsDataSource INSTANCE = null;

    private static HashMap<String, Product> mCachedProducts = null;

    private MemoryProductsDataSource() {
    }

    public static MemoryProductsDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MemoryProductsDataSource();
        }
        return INSTANCE;
    }

    @Override
    public List<Product> find(@NonNull Query query) {
        checkNotNull(query, "query no puede ser null");

        ArrayList<Product> products = Lists.newArrayList(mCachedProducts.values());
        ProductsSelector selector = new ProductsSelector(query);
        return selector.selectListRows(products);
    }

    @Override
    public void save(Product product) {
        if (mCachedProducts == null) {
            mCachedProducts = new LinkedHashMap<>();
        }
        mCachedProducts.put(product.getCode(), product);
    }


    @Override
    public void deleteAll() {
        if (mCachedProducts == null) {
            mCachedProducts = new LinkedHashMap<>();
        }
        mCachedProducts.clear();
    }


    @Override
    public boolean mapIsNull() {
        return mCachedProducts == null;
    }
}
