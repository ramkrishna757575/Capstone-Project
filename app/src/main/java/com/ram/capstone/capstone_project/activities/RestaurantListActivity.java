package com.ram.capstone.capstone_project.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ram.capstone.capstone_project.R;
import com.ram.capstone.capstone_project.adapters.TabAdapter;
import com.ram.capstone.capstone_project.fragments.RestaurantDetailFragment;
import com.ram.capstone.capstone_project.fragments.RestaurantListFragment;

public class RestaurantListActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        init();
        setupTabs();
    }

    private void init(){
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        nestedScrollView.setFillViewport(true);
    }

    private void setupTabs() {
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new RestaurantListFragment(), getString(R.string.tab_title_nearby));
        adapter.addFragment(new RestaurantDetailFragment(), getString(R.string.tab_title_bookmarked));
        viewPager.setAdapter(adapter);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }
}
