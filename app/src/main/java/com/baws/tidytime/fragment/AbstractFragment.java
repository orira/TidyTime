package com.baws.tidytime.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.baws.tidytime.TidyTimeApplication;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by wadereweti on 23/07/14.
 */
public class AbstractFragment extends Fragment {
    ObjectGraph mFragmentObjectGraph;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFragmentObjectGraph = TidyTimeApplication.get().createScopedGraph(getModules().toArray());
        mFragmentObjectGraph.inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFragmentObjectGraph = null;
    }

    protected List<Object> getModules() {
        return Arrays.<Object>asList();
    }
}
