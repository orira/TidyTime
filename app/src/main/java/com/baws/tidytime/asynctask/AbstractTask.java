package com.baws.tidytime.asynctask;

import android.os.AsyncTask;

import com.squareup.otto.Bus;

/**
 * Created by wadereweti on 22/07/14.
 */
public abstract class AbstractTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    protected final Bus mBus;
    protected boolean mWorking = false;

    protected AbstractTask(Bus bus) {
        mBus = bus;
    }

    public boolean isWorking() {
        return mWorking;
    }

    protected void mutateWorkingState() {
        mWorking = !mWorking;
    }
}
