package com.baws.tidytime.presenter;

import com.baws.tidytime.view.MainView;

/**
 * Created by wadereweti on 6/07/14.
 */
public class MainPresenterImpl implements MainPresenter {

    MainView mView;

    public MainPresenterImpl(MainView view) {
        mView = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onViewInitialised() {
        mView.initialiseViewPager();
        mView.initialiseTabStrip();
    }
}
