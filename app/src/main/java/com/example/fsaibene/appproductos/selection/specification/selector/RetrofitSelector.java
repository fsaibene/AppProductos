package com.example.fsaibene.appproductos.selection.specification.selector;

import java.util.List;

import retrofit2.Retrofit;

/**
 * Created by fsaibene on 15/3/2017.
 */

public interface RetrofitSelector<T>{
    void selectRetrofitRows (Retrofit retrofit, RetrofitSelectorCallback<T> callback);

    interface RetrofitSelectorCallback<T> {
        void onDataSelected(List<T> items);
        void onDataNotAvailable(String error);
    }
}
