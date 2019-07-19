package com.y.skin.igore;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.y.skin.R;

public class ChatFragment extends Fragment {

    View view;

    public static ChatFragment newInstance(){
        ChatFragment chat = new ChatFragment();
        return chat;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_chat,container,false);
        }
        ViewParent vp = view.getParent();
        if(vp != null){
            ((ViewGroup)vp).removeAllViews();
        }
        return view;
    }

}
