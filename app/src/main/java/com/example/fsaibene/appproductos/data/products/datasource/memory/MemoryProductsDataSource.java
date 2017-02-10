package com.example.fsaibene.appproductos.data.products.datasource.memory;

import com.example.fsaibene.appproductos.products.products.domain.criteria.ProductCriteria;
import com.example.fsaibene.appproductos.products.products.domain.model.Product;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by fsaibene on 8/2/2017.
 */
public class MemoryProductsDataSource implements IMemoryProductsDataSource{

    private static HashMap<String, Product> mCachedProducts;

    @Override
    public List<Product> find(ProductCriteria criteria) {
        ArrayList<Product> products =
                Lists.newArrayList(mCachedProducts.values());
        return criteria.match(products);
    }

    @Override
    public void save(Product product) {
        if (mCachedProducts == null){
            mCachedProducts = new LinkedHashMap<>();
        }
        mCachedProducts.put(product.getCode(), product);
    }

    @Override
    public void deleteAll() {
        if (mCachedProducts == null){
            mCachedProducts = new LinkedHashMap<>();
        }
        mCachedProducts.clear();
    }

    @Override
    public boolean mapIsNull() {
        return mCachedProducts == null;
    }
}
