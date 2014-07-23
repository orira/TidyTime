package com.baws.tidytime.presenter;

import com.baws.tidytime.view.MainView;

/**
 * Created by wadereweti on 6/07/14.
 */
public class MainPresenter {
    
    private final MainView mView;
    
    public MainPresenter(MainView view) {
        mView = view;
        init();
    }

    private void init() {
        mView.initialiseViewPager();
        mView.initialiseTabStrip();
    }
}
