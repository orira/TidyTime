package com.baws.tidytime.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baws.tidytime.R;
import com.baws.tidytime.activity.MainActivity;
import com.baws.tidytime.adapter.AssignedChoreAdapter;
import com.baws.tidytime.event.ChoreCreatedEvent;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.module.BusModule;
import com.baws.tidytime.view.AssignedView;
import com.baws.tidytime.view.MainView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by wadereweti on 6/07/14.
 */
public class AssignedFragment extends AbstractFragment implements AssignedView {

    private AssignedChoreAdapter mAdapter;

    @Inject Bus mBus;

    @InjectView(R.id.lv_assigned_chores)
    StickyListHeadersListView mAssignedChoresListView;

    public static AssignedFragment get() {
        AssignedFragment fragment = new AssignedFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new BusModule());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assigned, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mBus.register(this);
        mAdapter = new AssignedChoreAdapter(getActivity(), Child.getAll());
        mAssignedChoresListView.setAdapter(mAdapter);
        mAssignedChoresListView.setDivider(null);
        mAssignedChoresListView.setDividerHeight(0);
    }

    /*@Subscribe
    public void answerAvailable(ChoreCreatedEvent event) {
        mAdapter.setData(Child.getAll());
        mAdapter.notifyDataSetChanged();
    }*/
}
