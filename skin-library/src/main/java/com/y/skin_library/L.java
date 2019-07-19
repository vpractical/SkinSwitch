package com.y.skin_library;

import android.util.Log;

public class L {

    private static final String TAG = "Skin-Lib";

    public static void e(String msg) {
        if(BuildConfig.DEBUG){
            Log.e(TAG, msg);
        }
    }

}
