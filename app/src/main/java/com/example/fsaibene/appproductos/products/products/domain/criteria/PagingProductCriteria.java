package com.example.fsaibene.appproductos.products.products.domain.criteria;

import com.example.fsaibene.appproductos.products.products.domain.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fsaibene on 8/2/2017.
 */

public class PagingProductCriteria implements ProductCriteria {
    private final int mPage;
    private final int mLimit;
    public PagingProductCriteria(int page, int limit) {
        mPage = page;
        mLimit = limit;
    }

    @Override
    public List<Product> match(List<Product> products) {
        List<Product> criteriaProducts = new ArrayList<>();

        if(mLimit <= 0 || mPage <=0){
            return criteriaProducts;
        }
        int size = products.size();
        int numPages = size / mLimit;
        int a,b;

        if(mPage >numPages){
            return criteriaProducts;
        }

        a = (mPage - 1) * mLimit;
        if (a == size){
            return criteriaProducts;
        }
        b = a + mLimit;

        criteriaProducts = products.subList(a,b);
        return criteriaProducts;

    }
}
