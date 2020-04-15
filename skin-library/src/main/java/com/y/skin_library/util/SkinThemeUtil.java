package com.y.skin_library.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;

public class SkinThemeUtil {
    private static int[] APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS = {
            android.support.v7.appcompat.R.attr.colorPrimaryDark
    };
    private static int[] STATUSBAR_COLOR_ATTRS = {android.R.attr.statusBarColor, android.R.attr
            .navigationBarColor};

    /**
     * 获取attr中属性的id,获取这个id后再去SkinResource中加载皮肤包或默认包的值
     */
    public static int[]  getAttrId(Context context, int[] attrs) {
        int[] resIds = new int[attrs.length];
        TypedArray a = context.obtainStyledAttributes(attrs);
        for (int i = 0; i < attrs.length; i++) {
            resIds[i] = a.getResourceId(i, 0);
        }
        a.recycle();
        return resIds;
    }

    public static void updateStatusBarColor(Activity activity) {
        //5.0以上才能修改
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        //获得 statusBarColor 与 nanavigationBarColor (状态栏颜色)
        //当与 colorPrimaryDark  不同时 以statusBarColor为准
        int[] statusBarColorResId = getAttrId(activity, STATUSBAR_COLOR_ATTRS);
        //如果直接在style中写入固定颜色值(而不是 @color/XXX ) 获得0
        if (statusBarColorResId[0] != 0) {
            activity.getWindow().setStatusBarColor(SkinResource.getInstance().getColor
                    (statusBarColorResId[0]));
        } else {
            //获得 colorPrimaryDark
            int colorPrimaryDarkResId = getAttrId(activity, APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS)[0];
            if (colorPrimaryDarkResId != 0) {
                activity.getWindow().setStatusBarColor(SkinResource.getInstance().getColor
                        (colorPrimaryDarkResId));
            }
        }
        if (statusBarColorResId[1] != 0) {
            activity.getWindow().setNavigationBarColor(SkinResource.getInstance().getColor
                    (statusBarColorResId[1]));
        }
    }
}
