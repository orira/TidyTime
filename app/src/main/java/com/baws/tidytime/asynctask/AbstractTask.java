package com.baws.tidytime.asynctask;

import android.os.AsyncTask;

import com.baws.tidytime.TidyTimeApplication;
import com.squareup.otto.Bus;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;

/**
 * Created by wadereweti on 22/07/14.
 */
public abstract class AbstractTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private ObjectGraph mPresenterObjectGraph;

    @Inject
    Bus mBus;

    public AbstractTask() {
        mPresenterObjectGraph = TidyTimeApplication.get().createScopedGraph(getModules().toArray());
        mPresenterObjectGraph.inject(this);
    }

    protected List<Object> getModules() {
        //return Arrays.<Object>asList(new BusModule());
        return Arrays.<Object>asList();
    };
}
