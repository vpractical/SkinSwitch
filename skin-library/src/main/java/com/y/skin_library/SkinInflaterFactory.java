package com.y.skin_library;

import android.app.Activity;
import android.content.Context;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.y.skin_library.util.SkinAttribute;
import com.y.skin_library.util.SkinThemeUtil;

import java.lang.reflect.Constructor;
import java.util.Observable;
import java.util.Observer;

public class SkinInflaterFactory implements LayoutInflater.Factory2, Observer {

    /**
     * view如TextView等都是在这3个包中
     */
    private static final String[] viewPks = {"android.widget.","android.view.","android.webkit."};
    private static final ArrayMap<String,Constructor<? extends View>> constructorCache = new ArrayMap<>();
    private static final Class[] constructorParams = new Class[]{Context.class,AttributeSet.class};

    private SkinAttribute skinAttribute;

    private Activity activity;

    public SkinInflaterFactory(Activity activity){
        this.activity = activity;
        skinAttribute = new SkinAttribute();
    }


    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = createViewByTag(name,context,attrs);
        if(view == null){
            view = createView(name,context,attrs);
        }

        if(view != null){
            //创建view后，设置属属性
            skinAttribute.load(view,attrs);
        }

        return view;
    }

    private View createViewByTag(String name, Context context, AttributeSet attrs){
        if(name.contains(".")){
            return null;
        }
        View view;
        for (String pk:viewPks) {
            view = createView(pk + name,context,attrs);
            if(view != null){
                return view;
            }
        }
        return null;
    }

    /**
     * 反射创建view
     */
    private View createView(String name, Context context, AttributeSet attrs){
        try {
            Constructor<? extends View> constructor = constructorCache.get(name);
            if(constructor == null){
                Class<? extends View> clazz = context.getClassLoader().loadClass(name).asSubclass(View.class);
                constructor = clazz.getConstructor(constructorParams);
                constructorCache.put(name,constructor);
            }
            return constructor.newInstance(context,attrs);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        SkinThemeUtil.updateStatusBarColor(activity);
        skinAttribute.apply();
    }
}
