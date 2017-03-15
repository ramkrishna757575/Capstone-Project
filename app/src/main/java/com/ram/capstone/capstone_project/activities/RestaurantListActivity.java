package com.ram.capstone.capstone_project.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ram.capstone.capstone_project.R;
import com.ram.capstone.capstone_project.adapters.TabAdapter;
import com.ram.capstone.capstone_project.fragments.BookmarkedRestaurantListFragment;
import com.ram.capstone.capstone_project.fragments.RestaurantDetailFragment;
import com.ram.capstone.capstone_project.fragments.RestaurantListFragment;
import com.ram.capstone.capstone_project.interfaces.INotifyTabChange;
import com.ram.capstone.capstone_project.misc.AppConstants;
import com.ram.capstone.capstone_project.misc.SharedPref;
import com.ram.capstone.capstone_project.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private NestedScrollView nestedScrollView;
    private boolean twoPaneMode;
    private List<INotifyTabChange> listeners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);
        listeners = new ArrayList<>();
        checkAndSetTwoPaneLayout();
        init();
        setupTabs();
        setupListeners();
    }

    private void checkAndSetTwoPaneLayout() {
        if(findViewById(R.id.two_pane_layout) != null) {
            CommonUtils.getSharedPreferenceEditor(this).putBoolean(SharedPref.TWO_PANE_MODE, true).apply();
            twoPaneMode = true;
        }
    }

    private void init(){
        if(!twoPaneMode) {
            nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
            nestedScrollView.setFillViewport(true);
        } else {
            RestaurantDetailFragment restaurantDetailFragment = new RestaurantDetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.panel2, restaurantDetailFragment, AppConstants.RESTAURANT_DETAIL_FRAGMENT_TAG)
                    .commit();
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    private void setupTabs() {
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new RestaurantListFragment(), getString(R.string.tab_title_nearby));
        adapter.addFragment(new BookmarkedRestaurantListFragment(), getString(R.string.tab_title_bookmarked));
        viewPager.setAdapter(adapter);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }

    private void setupListeners() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(listeners != null && listeners.size() > 0 && twoPaneMode) {
                    for (INotifyTabChange listener : listeners) {
                        listener.tabChanged(position);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setOnTabChangeListener(INotifyTabChange listener) {
        if(listeners != null)
            listeners.add(listener);
    }

    public void removeOnTabChangeListener(INotifyTabChange listener) {
        if(listener != null)
            listeners.remove(listeners.indexOf(listener));
    }
}
