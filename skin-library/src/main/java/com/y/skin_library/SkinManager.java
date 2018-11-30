package com.y.skin_library;

import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SkinManager {
    private static SkinManager instance = new SkinManager();
    private Application application;
    private Resources mAppResource, mSkinResource;
    private String mSkinPackageName;
    private List<SkinNode> nodes = new ArrayList<>();

    public static SkinManager getInstance() {
        return instance;
    }

    public static void init(Application application) {
        instance.application = application;
        application.registerActivityLifecycleCallbacks(instance.lifecycleCallbacks);
    }

    public void loadSkin(String path) {
        try {
            mAppResource = application.getResources();
            mSkinResource = null;
            if (path != null) {
                AssetManager assetManager = AssetManager.class.newInstance();
                Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                addAssetPath.setAccessible(true);
                addAssetPath.invoke(assetManager, path);
                //获取外部Apk包名
                PackageManager pm = application.getPackageManager();
                PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
                mSkinPackageName = info.packageName;
                mSkinResource = new Resources(assetManager, mAppResource.getDisplayMetrics(), mAppResource.getConfiguration());
            }
            update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update() {
        for (SkinNode node : nodes) {
            String resName = mAppResource.getResourceEntryName(node.attrValue);//ic_launcher
            String resType = mAppResource.getResourceTypeName(node.attrValue);//drawable
            Log.e(resName, resType);
            int id = node.attrValue;
            if (mSkinResource != null) {
                id = mSkinResource.getIdentifier(resName, resType, mSkinPackageName);
            }

            switch (node.attrName) {
                case "background":
                    Object value = background(id, resType);
                    if (value instanceof Integer) {
                        node.view.setBackgroundColor((Integer) value);
                    } else {
                        node.view.setBackground((Drawable) value);
                    }
                    break;
                case "src":
                    value = src(id, resType);
                    if (value instanceof Integer) {
                        node.view.setBackgroundColor((Integer) value);
                    } else {
                        node.view.setBackground((Drawable) value);
                    }
                    break;
                case "testColor":
                    ((TextView) node.view).setTextColor(textcolor(id));
                    break;
            }
        }
    }

    private Object background(int id, String resType) {
        boolean c = resType.equals("color");
        if (mSkinResource != null) {
            return c ? mSkinResource.getColor(id) : mSkinResource.getDrawable(id);
        } else {
            return c ? mAppResource.getColor(id) : mAppResource.getDrawable(id);
        }
    }

    private Object src(int id, String resType) {
        boolean c = resType.equals("color");
        if (mSkinResource != null) {
            return c ? mSkinResource.getColor(id) : mSkinResource.getDrawable(id);
        } else {
            return c ? mAppResource.getColor(id) : mAppResource.getDrawable(id);
        }
    }

    private int textcolor(int id){
        if (mSkinResource != null) {
            return mSkinResource.getColor(id);
        } else {
            return mAppResource.getColor(id);
        }
    }

    public void add(SkinNode node) {
        nodes.add(node);
    }

    private Application.ActivityLifecycleCallbacks lifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//            SkinViewFactory skinFactory = new SkinViewFactory(activity);
//            LayoutInflater.from(activity).setFactory2(skinFactory);
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

        }
    };
}
