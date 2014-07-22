package com.baws.tidytime.asynctask;

import android.os.AsyncTask;

import com.baws.tidytime.TidyTimeApplication;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by wadereweti on 22/07/14.
 */
public abstract class AbstractTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private ObjectGraph mPresenterObjectGraph;

    public AbstractTask() {
        mPresenterObjectGraph = TidyTimeApplication.get().createScopedGraph(getModules().toArray());
        mPresenterObjectGraph.inject(this);
    }

    protected List<Object> getModules() {
        return Arrays.<Object>asList();
    };
}
