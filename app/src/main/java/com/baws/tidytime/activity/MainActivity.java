package com.baws.tidytime.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.baws.tidytime.R;
import com.baws.tidytime.adapter.MainViewPagerAdapter;
import com.baws.tidytime.fragment.AssignFragment;
import com.baws.tidytime.fragment.AssignedFragment;
import com.baws.tidytime.fragment.CompleteFragment;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.model.Chore;
import com.baws.tidytime.presenter.MainPresenter;
import com.baws.tidytime.view.MainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends AbstractActivity implements MainView {

    private static final String TAG = "MainActivity";
    MainPresenter mMainPresenter;

    @InjectView(R.id.vp_main)
    ViewPager mViewPager;

    ActionBar.TabListener mTabListener = new ActionBar.TabListener() {
        @Override
        public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
            switch (tab.getPosition()) {
                case 0:
                    mViewPager.setCurrentItem(0);
                    break;
                case 1:
                    mViewPager.setCurrentItem(1);
                    break;
                case 2:
                    mViewPager.setCurrentItem(2);
                    break;
            }
        }
        @Override
        public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {}
        @Override
        public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {}
    };

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            getActionBar().setSelectedNavigationItem(position);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
        @Override
        public void onPageScrollStateChanged(int state) {}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        constructChildren();
        intialiseTabs();
        mMainPresenter = new MainPresenter(this);
        mMainPresenter.init();
    }

    private void intialiseTabs() {
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText("Assigned").setTabListener(mTabListener));
        actionBar.addTab(actionBar.newTab().setText("Assign").setTabListener(mTabListener));
        actionBar.addTab(actionBar.newTab().setText("Complete").setTabListener(mTabListener));
    }

    private void constructChildren() {
        if (Child.getAll().size() == 0) {
            for (int i = 0; i < 3; i++) {
                Child child = Child.create(i);
                Chore.createChores(child, i);
            }
        }
    }

    @Override
    public void initialiseViewPager() {
        MainViewPagerAdapter mainAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), createFragments());
        mViewPager.setAdapter(mainAdapter);
        mViewPager.setOnPageChangeListener(pageChangeListener);
    }

    @Override
    public void updateAssignedChores() {
        AssignedFragment assignedFragment = (AssignedFragment) ((MainViewPagerAdapter) mViewPager.getAdapter()).getItem(0);
        assignedFragment.updateView();

    }

    private List<Fragment> createFragments() {
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(AssignedFragment.get());
        fragments.add(AssignFragment.get(this));
        fragments.add(CompleteFragment.get());

        return fragments;
    }
}
