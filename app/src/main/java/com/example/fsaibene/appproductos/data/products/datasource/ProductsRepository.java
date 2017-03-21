package com.example.fsaibene.appproductos.data.products.datasource;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.GridLayout;

import com.example.fsaibene.appproductos.data.products.datasource.cloud.ICloudProductsDataSource;
import com.example.fsaibene.appproductos.data.products.datasource.local.ILocalProductsDataSource;
import com.example.fsaibene.appproductos.data.products.datasource.local.LocalProductsDataSource;
import com.example.fsaibene.appproductos.data.products.datasource.memory.IMemoryProductsDataSource;
import com.example.fsaibene.appproductos.login.data.preferences.IUserPreferences;
import com.example.fsaibene.appproductos.products.products.domain.criteria.ProductCriteria;
import com.example.fsaibene.appproductos.products.products.domain.model.Product;
import com.example.fsaibene.appproductos.selection.specification.Query;
import com.example.fsaibene.appproductos.selection.specification.Specification;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by fsaibene on 8/2/2017.
 */

public class ProductsRepository implements IProductsRepository {

    private static ProductsRepository INSTANCE = null;

    private final IMemoryProductsDataSource mMemoryProductsDataSource;
    private final ILocalProductsDataSource mLocalProductsDataSource;
    private final ICloudProductsDataSource mCloudProductsDataSource;
    private final IUserPreferences mUserPreferences;

    private final ConnectivityManager mConnectivityManager;

    private boolean mReload = false;


    private ProductsRepository(@NonNull IMemoryProductsDataSource memoryDataSource,
                               @NonNull ILocalProductsDataSource localProductsDataSource,
                               @NonNull ICloudProductsDataSource cloudDataSource,
                               @NonNull IUserPreferences userPreferences,
                               Context context) {
        mMemoryProductsDataSource = checkNotNull(memoryDataSource);
        mLocalProductsDataSource = checkNotNull(localProductsDataSource);
        mCloudProductsDataSource = checkNotNull(cloudDataSource);
        mUserPreferences = checkNotNull(userPreferences);
        mConnectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

    }

    public static ProductsRepository getInstance(IMemoryProductsDataSource memoryDataSource,
                                                 ILocalProductsDataSource localProductsDataSource,
                                                 ICloudProductsDataSource cloudDataSource,
                                                 IUserPreferences userPreferences, Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ProductsRepository(memoryDataSource, localProductsDataSource, cloudDataSource,
                    userPreferences, context);
        }
        return INSTANCE;
    }

    @Override
    public void getProducts(@NonNull final Query query, final GetProductsCallback callback) {
        checkNotNull(query, "query no puede ser null");

        // ¿Hay Datos En Caché Y No Se Ordenó Recarga?
        if (!mMemoryProductsDataSource.mapIsNull() && !mReload) {
            callback.onProductsLoaded(mMemoryProductsDataSource.find(query));
            return;
        }

        // ¿Se ordenaron refrescar los datos?
        if (mReload) {
            getProductsFromServer(query, callback);
        } else {

            mLocalProductsDataSource.get(query, new ILocalProductsDataSource.GetCallback() {
                @Override
                public void onProductsLoaded(List<Product> products) {

                    Log.d("prepository","LISTA");
                    for (Product item : products) {
                        Log.d("id", item.getCode());//Los productos se muestran ok
                    }

                    refreshMemoryDataSource(products);
                    callback.onProductsLoaded(mMemoryProductsDataSource.find(query));
                }

                @Override
                public void onDataNotAvailable(String error) {
                    getProductsFromServer(query, callback);
                }
            });
        }
    }

    private void getProductsFromServer(final Query query, final GetProductsCallback callback) {

        if (!isOnline()) {
            // El Refresco No Pudo Ser
            mReload = false;
            callback.onDataNotAvailable("No hay conexión de red.");
            return;
        }

        // Obtener el token
        String token = mUserPreferences.getAccessToken();

        mCloudProductsDataSource.getProducts(
                query, token, new ICloudProductsDataSource.ProductServiceCallback() {
                    @Override
                    public void onLoaded(List<Product> products) {
                        Log.d("GETPRODFROMSERVER", "LLEGO");

                        for (Product item : products) {
                            Log.d("GETPRODFROMSERVER", item.getCode());
                        }
                        refreshMemoryDataSource(products);
                        refreshLocalDataSource(products);
                        callback.onProductsLoaded(mMemoryProductsDataSource.find(query));
                    }

                    @Override
                    public void onError(String error) {
                        callback.onDataNotAvailable(error);
                    }
                }
        );
    }

    private void refreshMemoryDataSource(List<Product> products) {
        mMemoryProductsDataSource.deleteAll();
        for (Product product : products) {
            Log.d("MEMORYLIST", product.getCode());//Los productos se muestran ok
            mMemoryProductsDataSource.save(product);
        }
        mReload = false;
    }

    private void refreshLocalDataSource(List<Product> products) {
        mLocalProductsDataSource.delete(null);
        for (Product product : products) {
            Log.d("LOCALREFRESHLIST", product.getCode());

            mLocalProductsDataSource.save(product);
        }
        mReload = false;
    }

    private boolean isOnline() {
        NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }

    @Override
    public void refreshProducts() {
        mReload = true;
    }

}
