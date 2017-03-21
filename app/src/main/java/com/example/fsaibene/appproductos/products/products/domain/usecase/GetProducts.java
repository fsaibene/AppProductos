package com.example.fsaibene.appproductos.products.products.domain.usecase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.fsaibene.appproductos.data.products.datasource.IProductsRepository;
import com.example.fsaibene.appproductos.products.products.domain.model.Product;
import com.example.fsaibene.appproductos.selection.specification.Query;
import com.example.fsaibene.appproductos.selection.specification.Specification;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by fsaibene on 9/3/2017.
 */

public class GetProducts implements IGetProducts {
    private IProductsRepository mProductsRepository;

    public GetProducts(IProductsRepository productsRepository) {
        mProductsRepository = checkNotNull(productsRepository, "productsRepository no puede ser null");
    }
    @Override
    public void getProducts(@NonNull final Query query, boolean forceLoad,
                            final GetProductsCallback callback) {
        checkNotNull(query, "query no puede ser null");
        checkNotNull(callback, "callback no puede ser null");


        if (forceLoad) {
            mProductsRepository.refreshProducts();
        }

        mProductsRepository.getProducts(query, new IProductsRepository.GetProductsCallback() {
                    @Override
                    public void onProductsLoaded(List<Product> products) {
                        Log.d("LISTAPRODUCTOSsss","LISTA");
                        for (Product item : products) {
                            Log.d("id", item.getCode());
                        }

                        checkNotNull(products, "products no puede ser null");

                        callback.onSuccess(products);
                    }

                    @Override
                    public void onDataNotAvailable(String error) {
                        callback.onError(error);
                    }
                }
        );
    }
}
