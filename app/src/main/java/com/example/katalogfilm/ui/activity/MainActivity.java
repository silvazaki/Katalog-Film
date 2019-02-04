package com.example.katalogfilm.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.katalogfilm.R;
import com.example.katalogfilm.adapter.PagerAdapter;
import com.example.katalogfilm.ui.fragment.FavoriteFragment;
import com.example.katalogfilm.ui.fragment.NowPlayingFragment;
import com.example.katalogfilm.ui.fragment.SettingFragment;
import com.example.katalogfilm.ui.fragment.UpcomingFragment;

public class MainActivity extends AppCompatActivity {


    ViewPager viewPager;
    TabLayout tabLayout;
    LinearLayout searchLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        searchLayout = findViewById(R.id.search_view);


        tabLayout.setupWithViewPager(viewPager);
        initPager();
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });

        viewPager.setCurrentItem(0, false);

    }

    private void initPager() {
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new NowPlayingFragment(), getResources().getString(R.string.title_now_playing));
        pagerAdapter.addFragment(new UpcomingFragment(), getResources().getString(R.string.title_upcoming));
        pagerAdapter.addFragment(new FavoriteFragment(), getResources().getString(R.string.title_favorite));
        pagerAdapter.addFragment(new SettingFragment(), "Setting");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_date);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_update);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_favorite);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_settings);
        int limit = (pagerAdapter.getCount() > 1 ? pagerAdapter.getCount() - 1 : 1);
        viewPager.setOffscreenPageLimit(limit);
    }
}
