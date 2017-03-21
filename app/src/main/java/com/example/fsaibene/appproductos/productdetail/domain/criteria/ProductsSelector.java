package com.example.fsaibene.appproductos.productdetail.domain.criteria;

import android.content.ContentResolver;
import android.database.Cursor;
import android.util.Log;

import com.example.fsaibene.appproductos.data.products.datasource.local.AsyncOpsProductsHandler;
import com.example.fsaibene.appproductos.external.sqlite.DatabaseContract;
import com.example.fsaibene.appproductos.external.sqlite.DatabaseContract.Products;
import com.example.fsaibene.appproductos.products.products.domain.model.Product;
import com.example.fsaibene.appproductos.selection.specification.MemorySpecification;
import com.example.fsaibene.appproductos.selection.specification.ProviderSpecification;
import com.example.fsaibene.appproductos.selection.specification.Query;
import com.example.fsaibene.appproductos.selection.specification.selector.ListSelector;
import com.example.fsaibene.appproductos.selection.specification.selector.ProviderSelector;
import com.example.fsaibene.appproductos.selection.specification.selector.RetrofitSelector;
import com.example.fsaibene.appproductos.util.CollectionsUtils;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Retrofit;

/**
 * Created by fsaibene on 15/3/2017.
 */

public class ProductsSelector implements ListSelector<Product>,
        ProviderSelector<Product>,
        RetrofitSelector<Product> {

    private final Query mQuery;

    public ProductsSelector(Query mQuery) {
        this.mQuery = mQuery;
    }

    @Override
    public List<Product> selectListRows(List<Product> items) {
        final MemorySpecification<Product> memorySpecification
                = (MemorySpecification<Product>) mQuery.getSpecification();
        List<Product> affectedProducts;
        Comparator<Product> comparator = mCodeComparator;

        //Filtro
        affectedProducts = new ArrayList<>(
                Collections2.filter(items, new Predicate<Product>() {
                    @Override
                    public boolean apply(Product product) {
                        return memorySpecification == null || memorySpecification.isSatisfiedBy(product);
                    }
                })
        );
        //Orden
        if (mQuery.getFieldSort() != null){
            switch (mQuery.getFieldSort()){
                case "name":
                    comparator = mNameComparator;
                    break;
                //añadir un caso para cada comparador
            }
        }
        Collections.sort(affectedProducts,comparator);
        //Elegir pagina
        affectedProducts = CollectionsUtils.getPage(affectedProducts, mQuery.getPageNumber(), mQuery.getPageSize());
        return affectedProducts;
    }

    @Override
    public void selectProviderRows(ContentResolver contentResolver,
                                   final ProviderSelectorCallback<Product> callback) {
        ProviderSpecification specification = (ProviderSpecification) mQuery.getSpecification();

        // TODO: Traduce a parámetros y segmentos los atributos de mQuery y concatenalos a la URI

        AsyncOpsProductsHandler handler = new AsyncOpsProductsHandler(contentResolver);
        handler.setQueryListener(new AsyncOpsProductsHandler.AsyncOpListener() {
            @Override
            public void onQueryComplete(int token, Object cookie, Cursor cursor) {
                List<Product> products = new ArrayList<>();

                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String code = cursor.getString(cursor.getColumnIndex(Products.CODE));
                        String name = cursor.getString(cursor.getColumnIndex(Products.NAME));
                        String description =
                                cursor.getString(cursor.getColumnIndex(Products.DESCRIPTION));
                        String brand = cursor.getString(cursor.getColumnIndex(Products.BRAND));
                        float price = cursor.getFloat(cursor.getColumnIndex(Products.PRICE));
                        int unitsInStock =
                                cursor.getInt(cursor.getColumnIndex(Products.UNITS_IN_STOCK));
                        String imageUrl = cursor.getString(cursor.getColumnIndex(Products.IMAGE_URL));
                        Product product = new Product(code, name, description,
                                brand, price, unitsInStock, imageUrl);
                        products.add(product);
                    }

                }

                if (cursor != null) {
                    cursor.close();
                }

                if (products.isEmpty()) {
                    callback.onDataNotAvailable("No se encontró ningún producto");
                } else {

                    callback.onDataSelected(products);
                }
            }
        });

        handler.startQuery(0, null, specification.asProvider(), null, null, null, null);

    }

    @Override
    public void selectRetrofitRows(Retrofit retrofit, RetrofitSelectorCallback<Product> callback) {

    }
    private Comparator<Product> mCodeComparator = new Comparator<Product>() {
        @Override
        public int compare(Product o1, Product o2) {
            if (mQuery.getSortOrder() == Query.ASC_ORDER) {
                return o1.getCode().compareTo(o2.getCode());
            } else {
                return o2.getCode().compareTo(o1.getCode());
            }
        }
    };
    private Comparator<Product> mNameComparator = new Comparator<Product>() {
        @Override
        public int compare(Product o1, Product o2) {
            if (mQuery.getSortOrder() == Query.ASC_ORDER) {
                return o1.getName().compareTo(o2.getName());
            } else {
                return o2.getName().compareTo(o1.getName());
            }
        }
    };
}
