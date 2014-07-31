package com.baws.tidytime.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.baws.tidytime.TidyTimeApplication;
import com.squareup.otto.Bus;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.ObjectGraph;

/**
 * Created by wadereweti on 23/07/14.
 */
public class AbstractFragment extends Fragment {
    ObjectGraph mFragmentObjectGraph;

    @Inject
    Bus mBus;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFragmentObjectGraph = TidyTimeApplication.get().createScopedGraph(getModules().toArray());
        mFragmentObjectGraph.inject(this);
        mBus.register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
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
