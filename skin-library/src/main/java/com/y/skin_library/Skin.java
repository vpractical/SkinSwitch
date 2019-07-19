package com.y.skin_library;

import android.app.Application;
import android.text.TextUtils;

import com.y.skin_library.util.SkinPreference;
import com.y.skin_library.util.SkinResource;

import java.util.Observable;

public class Skin extends Observable {
    private static Skin instance = new Skin();

    public static Skin getInstance(){
        return instance;
    }

    public static void init(Application application) {
        SkinPreference.init(application);
        SkinResource.getInstance().init(application);

        application.registerActivityLifecycleCallbacks(new AppLifeCallback());

        loadSkin(SkinPreference.get());
    }

    /**
     * 加载皮肤包
     */
    public static void loadSkin(String path) {
        if (TextUtils.isEmpty(path)) {
            SkinPreference.set(null);
            SkinResource.getInstance().reset();
        } else {
            SkinPreference.set(path);
            SkinResource.getInstance().set(path);
        }
        instance.setChanged();
        instance.notifyObservers();
    }

}
