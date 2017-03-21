package com.example.fsaibene.appproductos.products.products;

import android.util.Log;
import android.widget.Toast;

import com.example.fsaibene.appproductos.data.products.datasource.IProductsRepository;
import com.example.fsaibene.appproductos.data.products.datasource.ProductsRepository;
import com.example.fsaibene.appproductos.login.data.IUsersRepository;
import com.example.fsaibene.appproductos.login.data.UsersRepository;
import com.example.fsaibene.appproductos.products.products.domain.criteria.AllProductsSpecification;
import com.example.fsaibene.appproductos.products.products.domain.criteria.PagingProductCriteria;
import com.example.fsaibene.appproductos.products.products.domain.criteria.ProductCriteria;
import com.example.fsaibene.appproductos.products.products.domain.model.Product;
import com.example.fsaibene.appproductos.products.products.domain.usecase.IGetProducts;
import com.example.fsaibene.appproductos.selection.specification.Query;
import com.example.fsaibene.appproductos.selection.specification.Specification;
import com.google.common.base.Preconditions;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by fsaibene on 7/2/2017.
 */

public class ProductsPresenter implements ProductsMvp.Presenter {
    private final ProductsMvp.View mProductsView;
    private final IGetProducts mGetProducts;
    private final IUsersRepository mUsersRepository;

    private static final int PRODUCTS_LIMIT = 20;

    private boolean mIsFirstLoad = true;
    private int mCurrentPage = 1;


    public ProductsPresenter(ProductsMvp.View productsView, IGetProducts getProducts,
                             IUsersRepository usersRepository) {
        mProductsView = checkNotNull(productsView, "productsView no puede ser null");
        mGetProducts = checkNotNull(getProducts, "getProducts no puede ser null");
        mUsersRepository = checkNotNull(usersRepository, "usersRepository no puede ser null");
    }

    @Override
    public void loadProducts(final boolean reload) {

        if (reload || mIsFirstLoad) {
            mProductsView.showLoadingState(true);
            mCurrentPage = 1; // Reset...
        } else {
            mProductsView.showLoadMoreIndicator(true);
            mCurrentPage++;
        }

        // Construir Query...
        Query query = new Query(
                new AllProductsSpecification(), // Filtro
                "name", Query.ASC_ORDER,        // Orden
                mCurrentPage, PRODUCTS_LIMIT);  // Paginado

        // Retornar Productos...
        mGetProducts.getProducts(query, reload, new IGetProducts.GetProductsCallback() {
            @Override
            public void onSuccess(List<Product> products) {
                mProductsView.showLoadingState(false);
                processProducts(products, reload||mIsFirstLoad);
                mIsFirstLoad = false;

            }

            @Override
            public void onError(String error) {
                mProductsView.showLoadingState(false);
                mProductsView.showLoadMoreIndicator(false);
                mProductsView.showProductsError(error);
            }
        });

    }

    @Override
    public void openProductDetails(String productCode) {
        Preconditions.checkNotNull(productCode, "productCode no puede ser null");
        mProductsView.showProductDetailScreen(productCode);
    }

    @Override
    public void logOut() {
        mUsersRepository.closeSession();
        mProductsView.showLoginScreen();
    }

    private void processProducts(List<Product> products, boolean reload) {
        if (products.isEmpty()) {
            if (reload) {
                mProductsView.showEmptyState();
            } else {
                mProductsView.showLoadMoreIndicator(false);
            }
            mProductsView.allowMoreData(false);
        } else {

            if (reload) {
                mProductsView.showProducts(products);
            } else {
                mProductsView.showLoadMoreIndicator(false);
                mProductsView.showProductsPage(products);
            }

            mProductsView.allowMoreData(true);
        }
    }

}
