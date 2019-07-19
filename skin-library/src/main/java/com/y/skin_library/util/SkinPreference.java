package com.y.skin_library.util;

import android.app.Application;
import android.content.Context;

/**
 * 保存当前使用的扩展皮肤
 */
public class SkinPreference {

    private static final String SKIN_PATH_KEY = "skin_path_sp";

    private static Application application;


    public static void init(Application application){
        SkinPreference.application = application;
    }

    public static String get(){
        return application.getSharedPreferences(SKIN_PATH_KEY, Context.MODE_PRIVATE).getString(SKIN_PATH_KEY,null);
    }

    public static void set(String path){
        application.getSharedPreferences(SKIN_PATH_KEY,Context.MODE_PRIVATE).edit().putString(SKIN_PATH_KEY,path).apply();
    }

}
