package com.example.fsaibene.appproductos;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.fsaibene.appproductos.di.DependencyProvider;
import com.example.fsaibene.appproductos.productdetail.presentation.ProductDetailFragment;
import com.example.fsaibene.appproductos.productdetail.presentation.ProductDetailPresenter;
import com.example.fsaibene.appproductos.products.products.ProductsFragment;
import com.example.fsaibene.appproductos.products.products.ProductsMvp;
import com.example.fsaibene.appproductos.products.products.ProductsPresenter;
import com.example.fsaibene.appproductos.util.ActivityUtils;
import com.google.common.base.Preconditions;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by fsaibene on 14/3/2017.
 */

public class ProductsMvpController {
    private final FragmentActivity mProductsActivity;

    @Nullable private final String mProductCode;

    private ProductsTabletPresenter mProductsTabletPresenter;

    private ProductsPresenter mProductsPresenter;

    private ProductsMvpController(FragmentActivity productsActivity, String productCode) {
        mProductsActivity = productsActivity;
        mProductCode = productCode;
    }

    public static ProductsMvpController createProductsMvp(@NonNull AppCompatActivity productstActivity,
                                                          @Nullable String productCode) {
        Preconditions.checkNotNull(productstActivity);

        ProductsMvpController productsMvpController =
                new ProductsMvpController(productstActivity, productCode);

        productsMvpController.initProductsMvp();

        return productsMvpController;
    }

    private void initProductsMvp() {
        if (ActivityUtils.isTwoPane(mProductsActivity)) {
            createTabletElements();
        } else {
            createPhoneElements();
        }
    }

    private void createPhoneElements() {
        ProductsFragment productsFragment = findOrCreateProductsFragment(R.id.contentFrame_list);
        mProductsPresenter = createListPresenter(productsFragment);
        Log.d("asdasd","PHONEELEMENTS");
        productsFragment.setPresenter(mProductsPresenter);
    }

    private ProductsPresenter createListPresenter(ProductsMvp.View productsView) {
        return new ProductsPresenter(
                productsView,
                DependencyProvider.provideGetProducts(mProductsActivity),
                DependencyProvider.provideUsersRepository(mProductsActivity));

    }

    private ProductsFragment findOrCreateProductsFragment(int container) {
        ProductsFragment productsFragment = (ProductsFragment)
                getSupportFragmentManager().findFragmentById(container);

        if (productsFragment == null) {
            productsFragment = ProductsFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(container, productsFragment)
                    .commit();
        }

        return productsFragment;
    }

    private FragmentManager getSupportFragmentManager() {
        return mProductsActivity.getSupportFragmentManager();
    }

    private void createTabletElements(){
        //Fragmento 1, Lista
        ProductsFragment productsFragment = findOrCreateProductsFragment(R.id.contentFrame_list);
        mProductsPresenter = createListPresenter(productsFragment);

        //Fradgmento2 , Detalle
        ProductDetailFragment pDF = findOrCreateProductDetailFragmentForTablet();
        ProductDetailPresenter taskDetailPresenter = createProductDetailPresenter(pDF);

        //Conectas ambos fragmentos con el presenter de tablets
        mProductsTabletPresenter = new ProductsTabletPresenter(mProductsPresenter);
        productsFragment.setPresenter(mProductsTabletPresenter);
        pDF.setPresenter(mProductsTabletPresenter);
        mProductsTabletPresenter.setProductDetailPresenter(taskDetailPresenter);

    }

    private ProductDetailPresenter createProductDetailPresenter(
            ProductDetailFragment productDetailFragment) {
        return new ProductDetailPresenter(
                mProductCode,
                productDetailFragment,
                DependencyProvider.provideGetProducts(mProductsActivity));
    }

    private ProductDetailFragment findOrCreateProductDetailFragmentForTablet() {
        ProductDetailFragment detailFragment = (ProductDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame_detail);
        if (detailFragment == null) {
            detailFragment = ProductDetailFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentFrame_detail, detailFragment, "FragmentDetail")
                    .commit();
        }
        return detailFragment;
    }
    @Nullable
    public String getProductCode() {
        return mProductCode;
    }
}
