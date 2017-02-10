package com.example.fsaibene.appproductos.data.products.datasource;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.fsaibene.appproductos.data.products.datasource.cloud.ICloudProductsDataSource;
import com.example.fsaibene.appproductos.data.products.datasource.memory.IMemoryProductsDataSource;
import com.example.fsaibene.appproductos.products.products.domain.criteria.ProductCriteria;
import com.example.fsaibene.appproductos.products.products.domain.model.Product;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by fsaibene on 8/2/2017.
 */

public class ProductsRepository implements IProductsRepository {

    private final IMemoryProductsDataSource mMemoryProductsDataSource;
    private final ICloudProductsDataSource mCloudProductsDataSource;
    private final Context mContext;
    private boolean mReload;

    public ProductsRepository(IMemoryProductsDataSource memoryDataSource,ICloudProductsDataSource cloudDataSource, Context context){
        mMemoryProductsDataSource = checkNotNull(memoryDataSource);
        mCloudProductsDataSource = checkNotNull(cloudDataSource);
        mContext = checkNotNull(context);
    }
    @Override
    public void getProducts(GetProductsCallback callback, final ProductCriteria criteria) {
        if(!mMemoryProductsDataSource.mapIsNull() && !mReload){
            getProductsFromMemory(callback, criteria);
            return;
        }
        if (mReload){
            getProductsFromServer(callback, criteria);
        } else {
            List<Product> products = mMemoryProductsDataSource.find(criteria);
            if (products.size() > 0){
                callback.onProductsLoaded(products);
            } else {
                getProductsFromServer(callback, criteria);
            }
        }
    }

    @Override
    public void refreshProducts() {
        mReload = true;
    }
    private void getProductsFromMemory(GetProductsCallback callback, ProductCriteria criteria){
        callback.onProductsLoaded(mMemoryProductsDataSource.find(criteria));
    }
    private void getProductsFromServer(final GetProductsCallback callback, final ProductCriteria criteria){
        if(!isOnline()){
            callback.onDataNotAvailable("No hay conexion de red");
            return;
        }
        mCloudProductsDataSource.getProducts(
            new ICloudProductsDataSource.ProductsServiceCallback(){
                @Override
                public void onLoaded(List<Product> products){
                refreshMemoryDataSource(products);
                getProductsFromMemory(callback, criteria);
            }

                @Override
                public void onError(String error) {
                    callback.onDataNotAvailable(error);
                }

            }
        , criteria);
    }
    private boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }

    private void refreshMemoryDataSource(List<Product> products){
        mMemoryProductsDataSource.deleteAll();
        for (Product product : products){
            mMemoryProductsDataSource.save(product);
        }
        mReload = false;
    }

}
