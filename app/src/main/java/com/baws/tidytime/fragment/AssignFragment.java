package com.baws.tidytime.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.baws.tidytime.R;
import com.baws.tidytime.adapter.ChildSelectorAdapter;
import com.baws.tidytime.model.Child;
import com.iangclifton.android.floatlabel.FloatLabel;

import butterknife.ButterKnife;
import butterknife.InjectView;
import util.DateFormatter;

/**
 * Created by wadereweti on 6/07/14.
 */
public class AssignFragment extends Fragment {

    private static final float SCALE_FACTOR = 1.2f;

    @InjectView(R.id.fl_chore_type)
    FloatLabel mChoreType;

    @InjectView(R.id.et_chore_date)
    EditText mChoreDate;

    @InjectView(R.id.gv_child_selector)
    GridView mChildSelector;

    private View selectedView;

    public static AssignFragment get() {
        AssignFragment fragment = new AssignFragment();

        Bundle args = new Bundle();
        //args.putInt("someInt", someInt);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assign, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mChoreDate.setText(DateFormatter.getToday(getActivity()));
        mChildSelector.setAdapter(new ChildSelectorAdapter(Child.getAll(), getActivity()));
        mChildSelector.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (selectedView != null) {
                    // Animate old view to default
                    selectedView.animate().scaleX(1).scaleY(1);

                    // Set new view to selected
                    selectedView = view;
                } else {
                    selectedView = view;
                }

                view.animate().scaleX(SCALE_FACTOR).scaleY(SCALE_FACTOR);
            }
        });
    }
}
