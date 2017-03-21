package com.example.fsaibene.appproductos.selection.specification.selector;

import android.content.ContentResolver;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by fsaibene on 15/3/2017.
 */

public interface ProviderSelector<T> extends Selector {

    void selectProviderRows (ContentResolver contentResolver, ProviderSelectorCallback<T> callback);

    interface ProviderSelectorCallback<T> extends Selector{
        void onDataSelected(List<T> items);
        void onDataNotAvailable(String error);
    }
}
