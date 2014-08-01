package com.baws.tidytime.presenter;

import com.baws.tidytime.view.PresenterView;
import com.squareup.otto.Bus;

import dagger.ObjectGraph;

/**
 * Created by Raukawa on 7/2/2014.
 */
public abstract class AbstractPresenter {
    private ObjectGraph mPresenterObjectGraph;

    private final Bus mBus;

    public AbstractPresenter(Bus bus) {
        mBus = bus;
        mBus.register(this);
    }

    public abstract void onResume();
    public abstract void initialiseView();
}
