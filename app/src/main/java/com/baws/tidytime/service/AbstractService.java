package com.baws.tidytime.service;

import com.squareup.otto.Bus;

/**
 * Created by wadereweti on 5/08/14.
 */
public class AbstractService {
    protected final Bus mBus;
    protected boolean mWorking = false;

    public AbstractService(Bus bus) {
        mBus = bus;
        mBus.register(this);
    }

    public boolean isWorking() {
        return mWorking;
    }

    protected void mutateWorkingState() {
        mWorking = !mWorking;
    }
}
