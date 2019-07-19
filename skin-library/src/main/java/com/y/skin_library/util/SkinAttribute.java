package com.y.skin_library.util;

import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.y.skin_library.L;
import com.y.skin_library.SkinViewSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkinAttribute {
    private static List<String> skinAttrs = Arrays.asList(
            "src",
            "background",
            "text",
            "textColor"
    );

    private List<SkinView> skinViews = new ArrayList<>();

    public void load(View view, AttributeSet attrs) {
        L.e("SkinAttribute.load(view,attrs): " + view.getClass().getName());
        int count = attrs.getAttributeCount();
        List<Pair<String, Integer>> pairs = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String attrName = attrs.getAttributeName(i);
            if (!skinAttrs.contains(attrName)) {
                continue;
            }
            String attrValue = attrs.getAttributeValue(i);
            if (attrValue.startsWith("#")) {
                continue;
            }

            int resId = 0;
            if (attrValue.startsWith("?")) {
                int attrId = Integer.parseInt(attrValue.substring(1));
                resId = SkinThemeUtil.getAttrId(view.getContext(), new int[]{attrId})[0];
            }

            if (attrValue.startsWith("@")) {
                resId = Integer.parseInt(attrValue.substring(1));
            }

            if (resId <= 0) {
                continue;
            }

            pairs.add(new Pair<>(attrName, resId));
        }

        //有需要换肤的属性 || 是支持换肤的自定义view
        if (!pairs.isEmpty() ) {
            SkinView skinView = new SkinView(view, pairs);
            skinViews.add(skinView);
            skinView.apply();
        }else if ( view instanceof SkinViewSupport) {
            SkinView skinView = new SkinView(view, pairs);
            skinViews.add(skinView);
            skinView.apply();
        }
    }

    public void apply() {
        L.e("SkinAttribute.apply()");
        for (SkinView skinView : skinViews) {
            skinView.apply();
        }
    }

    static class SkinView {
        private View view;
        private List<Pair<String, Integer>> pairs;

        public SkinView(View view, List<Pair<String, Integer>> pairs) {
            this.view = view;
            this.pairs = pairs;
        }

        public void apply() {
            applySkinViewSupport(view);
            for (Pair<String, Integer> pair : pairs) {
                int id = pair.second;
                String resType;
                switch (pair.first) {
                    case "src":
                        resType = view.getContext().getResources().getResourceTypeName(id);
                        if(resType.equals("color")){
                            int color = SkinResource.getInstance().getColor(id);
                            ((ImageView)view).setImageDrawable(new ColorDrawable(color));
                        }else{
                            Drawable drawable = SkinResource.getInstance().getDrawable(id);
                            ((ImageView)view).setImageDrawable(drawable);
                        }
                        break;
                    case "background":
                        resType = view.getContext().getResources().getResourceTypeName(id);
                        if(resType.equals("color")){
                            int color = SkinResource.getInstance().getColor(id);
                            view.setBackgroundColor(color);
                        }else{
                            Drawable drawable = SkinResource.getInstance().getDrawable(id);
                            view.setBackground(drawable);
                        }
                        break;
                    case "text":
                        if(view instanceof TextView){
                            String str = SkinResource.getInstance().getString(id);
                            ((TextView)view).setText(str);
                        }
                        break;
                    case "textColor":
                        if(view instanceof TextView){
                            ColorStateList color = SkinResource.getInstance().getColorStateList(id);
                            ((TextView)view).setTextColor(color);
                        }
                        break;
                    default:

                        break;
                }
            }
        }

        private void applySkinViewSupport(View view) {
            if(view instanceof SkinViewSupport){
                ((SkinViewSupport) view).applySkin();
            }
        }

    }

}
