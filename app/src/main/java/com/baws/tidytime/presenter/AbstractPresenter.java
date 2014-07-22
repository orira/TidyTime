package com.baws.tidytime.presenter;

import com.baws.tidytime.TidyTimeApplication;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by Raukawa on 7/2/2014.
 */
public class AbstractPresenter {
    private ObjectGraph mPresenterObjectGraph;

    public AbstractPresenter() {
        mPresenterObjectGraph = TidyTimeApplication.get().createScopedGraph(getModules().toArray());
        mPresenterObjectGraph.inject(this);
    }

    protected List<Object> getModules() {
        return Arrays.<Object>asList();
    };
}
