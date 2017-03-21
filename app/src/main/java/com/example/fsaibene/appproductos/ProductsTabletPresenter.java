package com.example.fsaibene.appproductos;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.fsaibene.appproductos.productdetail.presentation.ProductDetailMvp;
import com.example.fsaibene.appproductos.productdetail.presentation.ProductDetailPresenter;
import com.example.fsaibene.appproductos.products.products.ProductsMvp;
import com.example.fsaibene.appproductos.products.products.ProductsPresenter;
import com.google.common.base.Preconditions;

import static com.google.common.base.Preconditions.*;

/**
 * Created by fsaibene on 13/3/2017.
 */

public class ProductsTabletPresenter implements ProductsMvp.Presenter, ProductDetailMvp.Presenter{

    @NonNull
    private ProductsPresenter mProductsPresenter;

    @Nullable
    private ProductDetailPresenter mProductDetailPresenter;

    public ProductsTabletPresenter(@NonNull ProductsPresenter productsPresenter) {
        mProductsPresenter = checkNotNull(productsPresenter);
    }

    @Nullable
    public ProductDetailPresenter getProductDetailPresenter() {
        return mProductDetailPresenter;
    }

    @Override
    public void loadProduct() {
        mProductDetailPresenter.loadProduct();
    }

    @Override
    public void loadProducts(boolean reload) {
        mProductsPresenter.loadProducts(reload);
    }

    @Override
    public void openProductDetails(String productCode) {
        mProductDetailPresenter.setProductCode(productCode);
        mProductDetailPresenter.loadProduct();
    }

    @Override
    public void logOut() {
        mProductsPresenter.logOut();
    }

    public void setProductDetailPresenter(ProductDetailPresenter productDetailPresenter) {
        mProductDetailPresenter = productDetailPresenter;
    }
}
