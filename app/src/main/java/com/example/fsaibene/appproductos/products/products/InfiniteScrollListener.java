package com.example.fsaibene.appproductos.products.products;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by fsaibene on 8/2/2017.
 */

public abstract class InfiniteScrollListener extends RecyclerView.OnScrollListener implements DataLoading{

    private static final int VISIBLE_THRESHOLD = 5;
    private final LinearLayoutManager mLayoutManager;
    private final DataLoading mDataLoading;

    public InfiniteScrollListener(LinearLayoutManager mLayoutManager, DataLoading mDataLoading) {
        this.mLayoutManager = checkNotNull(mLayoutManager);
        this.mDataLoading = checkNotNull(mDataLoading);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
         if (dy < 0 || mDataLoading.isLoadingData() || !mDataLoading.isThereMoreData())
             return;

        final int visibleItemCount = recyclerView.getChildCount();
        final int totalItemCount = mLayoutManager.getItemCount();
        final int firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

        if((totalItemCount - visibleItemCount) <= (firstVisibleItem + VISIBLE_THRESHOLD)){
            onLoadMore();
        }
    }

    @Override
    public boolean isLoadingData() {
        return false;
    }

    @Override
    public boolean isThereMoreData() {
        return false;
    }

    public abstract void onLoadMore();
}
