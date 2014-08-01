package com.baws.tidytime.presenter;

import com.squareup.otto.Bus;

/**
 * Created by Raukawa on 7/2/2014.
 */
public abstract class AbstractPresenter {

    private final Bus mBus;

    public AbstractPresenter(Bus bus) {
        mBus = bus;
        mBus.register(this);
    }

    public abstract void onResume();
}
