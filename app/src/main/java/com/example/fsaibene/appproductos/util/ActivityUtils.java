package com.example.fsaibene.appproductos.util;

import android.content.Context;

import com.example.fsaibene.appproductos.R;

/**
 * Created by fsaibene on 14/3/2017.
 */

public class ActivityUtils {
    public static boolean isTwoPane(Context context){
        return context.getResources().getBoolean(R.bool.twoPane);
    }
}
