package com.y.skin_library;

import android.view.View;

public class SkinNode {
    public View view;
    public String attrName;
    public int attrValue;

    public SkinNode(View view, String attrName, int attrValue) {
        this.view = view;
        this.attrName = attrName;
        this.attrValue = attrValue;
    }
}
