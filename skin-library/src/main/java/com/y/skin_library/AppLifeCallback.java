package com.y.skin_library;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;

import com.y.skin_library.util.SkinThemeUtil;

import java.lang.reflect.Field;
import java.util.Observer;

/**
 * activity的生命周期回调
 * 创建是加入view生成工厂，销毁时移除
 */
public class AppLifeCallback implements Application.ActivityLifecycleCallbacks {

    private ArrayMap<Activity,SkinInflaterFactory> map = new ArrayMap<>();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        SkinThemeUtil.updateStatusBarColor(activity);
        SkinInflaterFactory factory = new SkinInflaterFactory(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        try {
            //设置过factory后再设置会抛异常
            Field set = LayoutInflater.class.getDeclaredField("mFactorySet");
            set.setAccessible(true);
            set.setBoolean(inflater,false);
        }catch (Exception e){
            e.printStackTrace();
        }
        inflater.setFactory2(factory);
        Skin.getInstance().addObserver(factory);
        map.put(activity,factory);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Observer factory = map.remove(activity);
        Skin.getInstance().deleteObserver(factory);
    }
}
