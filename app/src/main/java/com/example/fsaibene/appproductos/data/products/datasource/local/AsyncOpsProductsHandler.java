package com.example.fsaibene.appproductos.data.products.datasource.local;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;

import java.lang.ref.WeakReference;

/**
 * Created by fsaibene on 15/3/2017.
 */

public class AsyncOpsProductsHandler extends AsyncQueryHandler {
    private WeakReference<AsyncOpListener> mListener;

    public interface AsyncOpListener {
        void onQueryComplete(int token, Object cookie, Cursor cursor);
    }

    public AsyncOpsProductsHandler(ContentResolver cr) {
        super(cr);
    }

    public void setQueryListener(AsyncOpListener listener) {
        mListener = new WeakReference<>(listener);
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        final AsyncOpListener listener = mListener.get();
        if (listener != null) {
            listener.onQueryComplete(token, cookie, cursor);
        } else if (cursor != null) {
            cursor.close();
        }
    }
}