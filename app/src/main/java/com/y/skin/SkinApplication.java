package com.y.skin;

import android.app.Application;

import com.y.skin_library.SkinManager;

public class SkinApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.init(this);
    }
}
