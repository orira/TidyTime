package com.baws.tidytime;

import com.activeandroid.app.Application;
import com.baws.tidytime.module.ApplicationModule;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by wadereweti on 7/07/14.
 */
public class TidyTimeApplication extends Application {

    private ObjectGraph mApplicationObjectGraph;

    private static TidyTimeApplication mTidyTimeApplication;

    public TidyTimeApplication() {
        mTidyTimeApplication = this;
    }

    public static TidyTimeApplication get() {
        return mTidyTimeApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationObjectGraph = ObjectGraph.create(getModules().toArray());
        mApplicationObjectGraph.inject(this);
    }

    private List<Object> getModules() {
        return Arrays.<Object>asList(new ApplicationModule(this));
    }

    public ObjectGraph getApplicationObjectGraph() {
        return mApplicationObjectGraph;
    }

    public ObjectGraph createScopedGraph(Object ... modules) {
        return mApplicationObjectGraph.plus(modules);
    }
}
