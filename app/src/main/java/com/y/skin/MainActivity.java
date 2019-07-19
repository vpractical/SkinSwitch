package com.y.skin;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.y.skin.igore.ChatFragment;
import com.y.skin.igore.MainPagerAdapter;
import com.y.skin.igore.NewsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 1.继承SkinViewSupport的自定义view如TabLayout才能换肤，第三方框架等使用需改进
 * 2.color值desc无效，待改进
 */
public class MainActivity extends AppCompatActivity  {

    private ViewPager vpContainer;
    private BottomNavigationBar navBar;

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<Pair<Integer,String>> elements = new ArrayList<>(5);
    private int currentIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vpContainer = findViewById(R.id.vp_main_container);
        navBar = findViewById(R.id.navbar_main);

        initFragment();
        initNavBar();
        initListener();

    }


    private void initFragment() {
        currentIndex = getIntent().getIntExtra("index",0);
        elements.clear();
        elements.add(new Pair<>(R.drawable.select_main_news_nav,"新闻"));
        elements.add(new Pair<>(R.drawable.select_main_chat_nav, "聊天"));

        fragmentList.clear();
        fragmentList.add(NewsFragment.newInstance());
        fragmentList.add(ChatFragment.newInstance());
        vpContainer.setOffscreenPageLimit(0);
        vpContainer.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), fragmentList));
        vpContainer.setCurrentItem(currentIndex);
    }

    private void initNavBar() {
        BadgeItem badgeItem = new BadgeItem().setBackgroundColor(Color.RED).setText("1");
        navBar.setMode(BottomNavigationBar.MODE_FIXED);
        navBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        navBar.setBarBackgroundColor(R.color.nav_bg);

        for (int i = 0; i < elements.size(); i++) {
            BottomNavigationItem bni = new BottomNavigationItem(elements.get(i).first, elements.get(i).second).setActiveColorResource(R.color.colorPrimaryDark);
            if(i == 1){
                bni.setBadgeItem(badgeItem);
            }
            navBar.addItem(bni);
        }
        navBar.setFirstSelectedPosition(currentIndex).initialise();//所有的设置需在调用该方法前完成
    }

    private void initListener() {
        vpContainer.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position != currentIndex) {
                    currentIndex = position;
                    navBar.selectTab(position);
                }
            }
        });

        navBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                //未选中 -> 选中
                if (position != currentIndex) {
                    currentIndex = position;
                    vpContainer.setCurrentItem(position);
                }
            }

            @Override
            public void onTabUnselected(int position) {
                //选中 -> 未选中

            }

            @Override
            public void onTabReselected(int position) {
                //选中 -> 选中

            }
        });


    }
}
