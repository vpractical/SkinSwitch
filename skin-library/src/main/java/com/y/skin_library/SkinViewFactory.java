package com.y.skin_library;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.Arrays;
import java.util.List;

public class SkinViewFactory implements LayoutInflater.Factory2{

    private List<String> limitAttrs = Arrays.asList(new String[]{"background","src","textColor"});


    private AppCompatActivity mActivity;

    public SkinViewFactory(){}


    public SkinViewFactory(AppCompatActivity activity){
        this.mActivity = activity;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = mActivity.getDelegate().createView(parent,name,context,attrs);
        int count = attrs.getAttributeCount();
        for (int i = 0; i < count; i++) {
            String attrName = attrs.getAttributeName(i);
            if(limitAttrs.contains(attrName)){
                String attrValue = attrs.getAttributeValue(i);

                Log.e("--- " + name,"--- " + attrName + "  : " + attrValue);

                if(attrValue.startsWith("#")) {
                    continue;
                }

                int id = 0;

                if(attrValue.startsWith("@")){
                    id = Integer.valueOf(attrValue.substring(1));
                }

                if(id > 0){
                    SkinManager.getInstance().add(new SkinNode(view,attrName,id));
                }
            }
        }
        return view;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return null;
    }
}
