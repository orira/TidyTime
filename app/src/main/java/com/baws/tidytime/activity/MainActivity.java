package com.baws.tidytime.activity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.baws.tidytime.R;
import com.baws.tidytime.adapter.MainViewPagerAdapter;
import com.baws.tidytime.fragment.AssignFragment;
import com.baws.tidytime.fragment.AssignedFragment;
import com.baws.tidytime.fragment.CompleteFragment;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.model.Chore;
import com.baws.tidytime.presenter.MainPresenter;
import com.baws.tidytime.transformer.ParallaxTransformer;
import com.baws.tidytime.typeface.RobotoTypeface;
import com.baws.tidytime.view.MainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import util.TypefaceUtil;


public class MainActivity extends AbstractActivity implements MainView {

    private static final String TAG = "MainActivity";
    MainPresenter mMainPresenter;

    @InjectView(R.id.tab_strip)
    PagerSlidingTabStrip mTabStrip;

    @InjectView(R.id.vp_main)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        constructChildren();
        mMainPresenter = new MainPresenter(this);
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
        mViewPager.setPageTransformer(true, new ParallaxTransformer());
    }

    @Override
    public void initialiseTabStrip() {
        mTabStrip.setViewPager(mViewPager);
        mTabStrip.setTypeface(TypefaceUtil.getFont(this, RobotoTypeface.LIGHT), 0);
        mTabStrip.setIndicatorColorResource(R.color.application_colour);
        mTabStrip.setIndicatorHeight(5);
        //mTabStrip.setTabBackground(getResources().getDrawable(R.drawable.selector_tab_strip));
        mTabStrip.setTabBackground(R.drawable.selector_tab_strip);
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
