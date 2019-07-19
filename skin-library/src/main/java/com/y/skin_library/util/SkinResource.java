package com.y.skin_library.util;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import java.lang.reflect.Method;

public class SkinResource {
    private static SkinResource instance = new SkinResource();
    public static SkinResource getInstance(){
        return instance;
    }

    private Application application;
    private String packageName;
    private Resources resourcesApp,resourcesSkin;
    private boolean isUsedSkin;

    public void init(Application app){
        application = app;
        resourcesApp = application.getResources();
    }

    /**
     * 设置皮肤包的包名和资源对象
     */
    public void set(String path){
        try {
            AssetManager am = AssetManager.class.newInstance();
            Method method = am.getClass().getMethod("addAssetPath",String.class);
            method.setAccessible(true);
            method.invoke(am,path);

            resourcesSkin = new Resources(am, resourcesApp.getDisplayMetrics(), resourcesApp.getConfiguration());

            PackageManager pm = application.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
            packageName = info.packageName;

            isUsedSkin = !TextUtils.isEmpty(info.packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 移除使用的皮肤包
     */
    public void reset(){
        packageName = null;
        resourcesSkin = null;
        isUsedSkin = false;
    }

    private int getResId(int id){
        if(!isUsedSkin){
            return id;
        }
        String resName = resourcesApp.getResourceEntryName(id);
        String resType = resourcesApp.getResourceTypeName(id);
        return resourcesSkin.getIdentifier(resName,resType,packageName);
    }

    public int getColor(int id){
        if(isUsedSkin){
            int skinId = getResId(id);
            if(skinId > 0){
                try {
                    return resourcesSkin.getColor(id);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return resourcesApp.getColor(id);
    }

    public Drawable getDrawable(int id){
        if(isUsedSkin){
            int skinId = getResId(id);
            if (skinId > 0) {
                return resourcesSkin.getDrawable(skinId);
            }
        }
        return resourcesApp.getDrawable(id);
    }

    public String getString(int id){
        if(isUsedSkin){
            int skinId = getResId(id);
            if(skinId > 0){
                return resourcesSkin.getString(skinId);
            }
        }
        return resourcesApp.getString(id);
    }

    /**
     * 这个是textColor用
     */
    public ColorStateList getColorStateList(int id) {
        if (isUsedSkin) {
            int skinId = getResId(id);
            if (skinId > 0) {
                return resourcesSkin.getColorStateList(skinId);
            }
        }
        return resourcesApp.getColorStateList(id);
    }
}
