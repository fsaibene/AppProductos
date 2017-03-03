package com.example.fsaibene.appproductos.products.products;

import com.example.fsaibene.appproductos.products.products.domain.model.Product;

import java.util.List;

/**
 * Created by fsaibene on 7/2/2017.
 */

public interface ProductsMvp {
    interface View {
        void showProducts(List<Product> products);
        void showLoadingState(boolean show);
        void showEmptyState();
        void showProductsError(String msg);
        void showProductsPage(List<Product> products);
        void showLoadMoreIndicator(boolean show);
        void allowMoreData(boolean show);
        void setPresenter(Presenter productsPresenter);
        void showLoginScreen();
    }

    interface Presenter{
        void loadProducts(boolean reload);
        void logOut();
    }
}
