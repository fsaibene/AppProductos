package com.example.fsaibene.appproductos.products.products.domain.criteria;

import com.example.fsaibene.appproductos.products.products.domain.model.Product;

import java.util.List;

/**
 * Created by fsaibene on 8/2/2017.
 */

public interface ProductCriteria {
    List<Product> match(List<Product> products);
}

