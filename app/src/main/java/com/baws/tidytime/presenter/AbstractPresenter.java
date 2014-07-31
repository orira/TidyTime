package com.baws.tidytime.presenter;

import com.baws.tidytime.TidyTimeApplication;
import com.baws.tidytime.view.PresenterView;
import com.squareup.otto.Bus;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;

/**
 * Created by Raukawa on 7/2/2014.
 */
public abstract class AbstractPresenter {
    private ObjectGraph mPresenterObjectGraph;

    @Inject
    Bus mBus;

    public AbstractPresenter() {
        mPresenterObjectGraph = TidyTimeApplication.get().createScopedGraph(getModules().toArray());
        mPresenterObjectGraph.inject(this);
        mBus.register(this);
    }

    protected List<Object> getModules() {
        //return Arrays.<Object>asList(new BusModule());
        return Arrays.<Object>asList();
    };

    /*@Subscribe
    public void viewRecreated(RecreatedViewEvent event) {
        mView = event.getView();
    }*/

    public abstract void onResume(PresenterView view);
    protected abstract void initialiseView();
}
