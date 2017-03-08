package com.example.fsaibene.appproductos.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.fsaibene.appproductos.data.products.datasource.ProductsRepository;
import com.example.fsaibene.appproductos.data.products.datasource.cloud.CloudProductsDataSource;
import com.example.fsaibene.appproductos.data.products.datasource.memory.MemoryProductsDataSource;
import com.example.fsaibene.appproductos.login.data.UsersRepository;
import com.example.fsaibene.appproductos.login.data.cloud.CloudUsersDataSource;
import com.example.fsaibene.appproductos.login.data.preferences.UserPrefs;
import com.example.fsaibene.appproductos.login.domain.entities.usecases.LoginInteractor;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by fsaibene on 8/2/2017.
 */

public class DependencyProvider {

    private static Context mContext;
    private static MemoryProductsDataSource memorySource = null;
    private static CloudProductsDataSource cloudSource = null;
    private static ProductsRepository mProductsRepository = null;

    private static UsersRepository sUsersRepository = null;
    private static CloudUsersDataSource sUsersCloudStore = null;
    private static UserPrefs sUserPrefs = null;

    private static LoginInteractor sLoginInteractor = null;

    public DependencyProvider() {
    }

    public static ProductsRepository provideProductsRepository(@NonNull Context context){
        mContext = checkNotNull(context);
        if (mProductsRepository == null){
            mProductsRepository = new ProductsRepository(getMemorySource(), getCloudSource(), context, getUserPrefs());
        }
        return  mProductsRepository;
    }
    public static MemoryProductsDataSource getMemorySource(){
        if (memorySource == null){
            memorySource = new MemoryProductsDataSource();
        }
        return memorySource;
    }
    public static CloudProductsDataSource getCloudSource(){
        if (cloudSource == null){
            cloudSource = new CloudProductsDataSource();
        }
        return cloudSource;
    }

    public static UsersRepository provideUsersRepository(Context context){
        if (sUsersRepository == null){
            sUsersRepository = new UsersRepository(usersCloudStore(), getUserPrefs(), context);
        }
        return sUsersRepository;
    }
    private static CloudUsersDataSource usersCloudStore() {
        if(sUsersCloudStore == null){
            sUsersCloudStore = CloudUsersDataSource.getInstance();
        }
        return sUsersCloudStore;
    }

    public static UserPrefs getUserPrefs(){
        if (sUserPrefs == null){
            sUserPrefs = UserPrefs.getInstance(mContext);// despues cambia la firma
        }
        return sUserPrefs;
    }

    public static LoginInteractor provideLoginInteractor(Context context){
        if (sLoginInteractor == null){
            sLoginInteractor = new LoginInteractor(provideUsersRepository(context));
        }
        return sLoginInteractor;
    }
}
