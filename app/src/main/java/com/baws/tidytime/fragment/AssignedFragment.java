package com.baws.tidytime.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baws.tidytime.R;
import com.baws.tidytime.activity.MainActivity;
import com.baws.tidytime.adapter.AssignedChoreAdapter;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.view.AssignedView;
import com.baws.tidytime.view.MainView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by wadereweti on 6/07/14.
 */
public class AssignedFragment extends Fragment implements AssignedView {

    @InjectView(R.id.lv_assigned_chores)
    StickyListHeadersListView mAssignedChoresListView;

    public static AssignedFragment get() {
        AssignedFragment fragment = new AssignedFragment();

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

        StickyListHeadersAdapter adapter = new AssignedChoreAdapter(getActivity(), Child.getAll());
        mAssignedChoresListView.setAdapter(adapter);
        mAssignedChoresListView.setDivider(null);
        mAssignedChoresListView.setDividerHeight(0);
    }

    @Override
    public void updateView() {
        ((AssignedChoreAdapter) mAssignedChoresListView.getAdapter()).notifyDataSetChanged();
    }
}
