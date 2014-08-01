package com.baws.tidytime.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baws.tidytime.R;

import butterknife.ButterKnife;

/**
 * Created by wadereweti on 6/07/14.
 */
public class CompleteFragment extends Fragment {


    public static CompleteFragment get() {
        CompleteFragment fragment = new CompleteFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complete, container, false);
        ButterKnife.inject(this, view);

        return view;
    }
}
