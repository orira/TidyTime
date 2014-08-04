package com.baws.tidytime.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baws.tidytime.R;
import com.baws.tidytime.adapter.AssignedChoreAdapter;
import com.baws.tidytime.event.RefreshChoresEvent;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.view.AssignedView;
import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by wadereweti on 6/07/14.
 */
public class AssignedChoreFragment extends AbstractFragment implements AssignedView {

    private AssignedChoreAdapter mAdapter;

    @InjectView(R.id.lv_assigned_chores)
    StickyListHeadersListView mAssignedChoresListView;

    public static AssignedChoreFragment get() {
        AssignedChoreFragment fragment = new AssignedChoreFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
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

        mAdapter = new AssignedChoreAdapter(getActivity(), Child.getAll());
        mAssignedChoresListView.setAdapter(mAdapter);
        mAssignedChoresListView.setDivider(null);
        mAssignedChoresListView.setDividerHeight(0);
    }

    @Subscribe
    public void answerAvailable(RefreshChoresEvent event) {
        mAdapter.setData(Child.getAll());
        mAdapter.notifyDataSetChanged();
    }
}
