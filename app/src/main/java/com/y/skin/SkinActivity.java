package com.y.skin;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.y.permissionlib.PermCat;
import com.y.permissionlib.PermissionCat;
import com.y.skin.view.Item;
import com.y.skin_library.Skin;

public class SkinActivity extends AppCompatActivity {

    public static final String PERM = "android.permission.READ_EXTERNAL_STORAGE";
//    public static final String[] PERMS = {
//            "android.permission.WRITE_EXTERNAL_STORAGE",
//            "android.permission.READ_EXTERNAL_STORAGE"
//    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
        load();
    }

    @PermCat(PERM)
    public void load() {
        if (PermissionCat.has(this, PERM)) {
            load2();
        } else {
            PermissionCat.request("寻找换肤文件", this, null, PERM);
        }
    }

    public void load2() {

        LinearLayout container = findViewById(R.id.container);

        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        final String path1 = root + "/skin_aa-debug.apk";
        final String path2 = root + "/skin_bb-debug.apk";

        Item item0 = findViewById(R.id.item0);
        item0.setPath("默认");
        item0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skin.loadSkin(null);
            }
        });

        Item item1 = new Item(this);
        item1.setPath(path1);
        container.addView(item1);
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skin.loadSkin(path1);
            }
        });

        Item item2 = new Item(this);
        item2.setPath(path2);
        container.addView(item2);
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skin.loadSkin(path2);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionCat.onRequestPermissionsResult(this, permissions, grantResults);
    }
}
