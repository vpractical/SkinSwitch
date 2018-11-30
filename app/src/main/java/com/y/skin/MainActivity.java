package com.y.skin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.y.skin_library.SkinManager;
import com.y.skin_library.SkinViewFactory;

public class MainActivity extends AppCompatActivity {
    public static final String SKIN_PATH_1 = Environment.getExternalStorageState() + "/skina.apk";

    private String path = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SkinViewFactory skinFactory = new SkinViewFactory(this);
        LayoutInflater.from(this).setFactory2(skinFactory);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btn_main_tob);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });

        findViewById(R.id.btn_main_skin_switch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchSkin();
            }
        });
    }

    private void switchSkin(){
        Log.e("path = ", SKIN_PATH_1);
        if(path == null){
            path = SKIN_PATH_1;
        }else{
            path = null;
        }

        SkinManager.getInstance().loadSkin(path);
    }
}
