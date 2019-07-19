package com.y.skin;

import android.app.Application;

import com.y.skin_library.Skin;

public class SkinApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Skin.init(this);
    }
}
