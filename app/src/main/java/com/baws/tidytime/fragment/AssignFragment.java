package com.baws.tidytime.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import com.baws.tidytime.R;
import com.baws.tidytime.adapter.ChildSelectorAdapter;
import com.baws.tidytime.fragment.dialog.CalendarDialogFragment;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.view.DateView;
import com.dd.CircularProgressButton;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import util.DateFormatter;

/**
 * Created by wadereweti on 6/07/14.
 */
public class AssignFragment extends Fragment implements DateView {

    private static final String TAG = "AssignFragment";

    @InjectView(R.id.sp_chore_zone)
    Spinner mChoreZoneSpinner;

    @InjectView(R.id.sp_chore_type)
    Spinner mChoreTypeSpinner;

    @InjectView(R.id.chore_date)
    EditText mChoreDate;

    @InjectView(R.id.sp_amount)
    Spinner mAmount;

    @InjectView(R.id.gv_child_selector)
    GridView mChildSelectorGridView;

    @InjectView(R.id.btn_create_chore)
    CircularProgressButton mButton;

    private AdapterView.OnItemSelectedListener zoneSelector = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            int chores = 0;

            switch (position) {
                case 1:
                    chores = R.array.bedroom_chores_array;
                    break;
                case 2:
                    chores = R.array.bathroom_chores_array;
                    break;
                case 3:
                    chores = R.array.kitchen_chores_array;
                    break;
                case 4:
                    chores = R.array.lounge_chores_array;
                    break;
                case 5:
                    chores = R.array.washhouse_chores_array;
                    break;
                case 6:
                    chores = R.array.garage_chores_array;
                    break;
                case 7:
                    chores = R.array.outside_chores_array;
                    break;
            }

            // A selection has occured display the spinner
            if (chores != 0) {
                ArrayAdapter<CharSequence> typeAdapter =
                        ArrayAdapter.createFromResource(getActivity(), chores, R.layout.spinner_item);
                typeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                mChoreTypeSpinner.setAdapter(typeAdapter);
                mChoreTypeSpinner.setVisibility(View.VISIBLE);
            } else {
                mChoreTypeSpinner.setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {}
    };

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

        ArrayAdapter<CharSequence> zoneAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.chore_zone_array, R.layout.spinner_item);
        zoneAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mChoreZoneSpinner.setAdapter(zoneAdapter);
        mChoreZoneSpinner.setOnItemSelectedListener(zoneSelector);

        mChoreDate.setText(DateFormatter.getToday(getActivity()));
        mChoreDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarDialogFragment calendar = new CalendarDialogFragment();
                calendar.setDateView(AssignFragment.this);
                calendar.show(getActivity().getSupportFragmentManager(), "calendarDialogFragment");
            }
        });

        ArrayAdapter<CharSequence> amountAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.chore_amount_array, R.layout.spinner_item);
        amountAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mAmount.setAdapter(amountAdapter);

        mChildSelectorGridView.setAdapter(new ChildSelectorAdapter(Child.getAll(), getActivity()));

        mButton.setIndeterminateProgressMode(true);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mButton.getProgress() == 0) {
                    mButton.setProgress(50);
                } else if (mButton.getProgress() == 100) {
                    mButton.setProgress(0);
                } else {
                    mButton.setProgress(100);
                }
            }
        });
    }

    @Override
    public void onDateSelected(Date date) {
        mChoreDate.setText(DateFormatter.formatDate(date));
    }
}
