package com.example.fsaibene.appproductos.data.products.datasource.memory;

import com.example.fsaibene.appproductos.products.products.domain.criteria.ProductCriteria;
import com.example.fsaibene.appproductos.products.products.domain.model.Product;

import java.util.List;

/**
 * Created by fsaibene on 8/2/2017.
 */

public interface IMemoryProductsDataSource {

    List<Product> find(ProductCriteria criteria);

    void save(Product product);
    void deleteAll();
    boolean mapIsNull();
}
