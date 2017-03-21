package com.example.fsaibene.appproductos.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.fsaibene.appproductos.data.products.datasource.ProductsRepository;
import com.example.fsaibene.appproductos.data.products.datasource.cloud.CloudProductsDataSource;
import com.example.fsaibene.appproductos.data.products.datasource.local.LocalProductsDataSource;
import com.example.fsaibene.appproductos.data.products.datasource.memory.MemoryProductsDataSource;
import com.example.fsaibene.appproductos.login.data.UsersRepository;
import com.example.fsaibene.appproductos.login.data.cloud.CloudUsersDataSource;
import com.example.fsaibene.appproductos.login.data.preferences.UserPrefs;
import com.example.fsaibene.appproductos.login.domain.entities.usecases.LoginInteractor;
import com.example.fsaibene.appproductos.products.products.domain.usecase.GetProducts;
import com.example.fsaibene.appproductos.selection.specification.Specification;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by fsaibene on 8/2/2017.
 */

public class DependencyProvider {

    private DependencyProvider() {
    }

    public static ProductsRepository provideProductsRepository(@NonNull Context context) {
        return ProductsRepository.getInstance(
                MemoryProductsDataSource.getInstance(),
                LocalProductsDataSource.getInstance(context.getContentResolver()),
                CloudProductsDataSource.getInstance(),
                UserPrefs.getInstance(context),
                context);
    }

    public static UsersRepository provideUsersRepository(@NonNull Context context) {
        return UsersRepository.getInstance(CloudUsersDataSource.getInstance(),
                UserPrefs.getInstance(context), context);
    }

    public static LoginInteractor provideLoginInteractor(@NonNull Context context) {
        return new LoginInteractor(provideUsersRepository(context));
    }

    public static GetProducts provideGetProducts(@NonNull Context context) {
        return new GetProducts(provideProductsRepository(context));
    }
}
