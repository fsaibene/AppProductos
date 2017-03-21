package com.example.fsaibene.appproductos.productdetail.presentation;

import com.example.fsaibene.appproductos.products.products.ProductsMvp;
import com.example.fsaibene.appproductos.products.products.domain.criteria.ProductCriteria;

/**
 * Created by fsaibene on 9/3/2017.
 */

public interface ProductDetailMvp {
    interface View{
        void showImage(String imageUrl);
        void showName(String name);
        void showPrice(String price);
        void showUnitsInStock(String unitsInStock);
        void showDescription(String description);
        void showProgressIndicator(boolean show);
        void showLoadError(String error);
        void showEmptyState();
        void setPresenter(ProductDetailMvp.Presenter productDetailPresenter);

        boolean isActive();
    }
    interface Presenter{
        void loadProduct();
    }

}
