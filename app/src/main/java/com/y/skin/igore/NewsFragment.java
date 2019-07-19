package com.y.skin.igore;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.y.skin.R;
import com.y.skin.SkinActivity;

public class NewsFragment extends Fragment {

    View view;

    public static NewsFragment newInstance(){
        NewsFragment chat = new NewsFragment();
        return chat;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_news,container,false);
            init();
        }
        ViewParent vp = view.getParent();
        if(vp != null){
            ((ViewGroup)vp).removeAllViews();
        }
        return view;
    }

    private void init() {
        view.findViewById(R.id.switch_skin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SkinActivity.class));
            }
        });
    }

}
