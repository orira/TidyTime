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
import android.widget.TextView;

import com.baws.tidytime.R;
import com.baws.tidytime.adapter.ChildSelectorAdapter;
import com.baws.tidytime.fragment.dialog.CalendarDialogFragment;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.view.ChildSelectorView;
import com.baws.tidytime.view.DateView;
import com.baws.tidytime.widget.RobotoTextView;
import com.dd.CircularProgressButton;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import util.DateFormatter;

/**
 * Created by wadereweti on 6/07/14.
 */
public class AssignFragment extends Fragment implements DateView, ChildSelectorView {

    private static final String TAG = "AssignFragment";

    private AdapterView.OnItemSelectedListener zoneSelector = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

            int choresArray = getChoresArray(position);

            if (choresArray != 0) {
                mChoreZone = ((TextView) view).getText().toString();

                // A selection has occured display the type spinner
                ArrayAdapter<CharSequence> typeAdapter =
                        ArrayAdapter.createFromResource(getActivity(), choresArray, R.layout.spinner_item);
                typeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                mChoreTypeSpinner.setAdapter(typeAdapter);
                mChoreTypeSpinner.setOnItemSelectedListener(typeSelector);
                mChoreTypeSpinner.setVisibility(View.VISIBLE);
                validateInput();
            } else {
                mChoreTypeSpinner.setVisibility(View.GONE);
                mChoreZone = null;
                mChoreType = null;
                validateInput();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {}

        private int getChoresArray(int position) {
            int choresArray = 0;

            switch (position) {
                case 1:
                    choresArray = R.array.bedroom_chores_array;
                    break;
                case 2:
                    choresArray = R.array.bathroom_chores_array;
                    break;
                case 3:
                    choresArray = R.array.kitchen_chores_array;
                    break;
                case 4:
                    choresArray = R.array.lounge_chores_array;
                    break;
                case 5:
                    choresArray = R.array.washhouse_chores_array;
                    break;
                case 6:
                    choresArray = R.array.garage_chores_array;
                    break;
                case 7:
                    choresArray = R.array.outside_chores_array;
                    break;
            }

            return choresArray;
        }
    };

    private AdapterView.OnItemSelectedListener typeSelector = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            mChoreType = ((TextView) view).getText().toString();
            validateInput();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private String mChoreZone;
    private String mChoreType;
    private Child mChildSelected;

    @InjectView(R.id.label_chore_selection)
    RobotoTextView mLabelChoreSelection;

    @InjectView(R.id.label_chore_date)
    RobotoTextView mLabelChoreDate;

    @InjectView(R.id.label_chore_amount)
    RobotoTextView mLabelChoreAmount;

    @InjectView(R.id.label_child)
    RobotoTextView mLabelChild;

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

        mChildSelectorGridView.setAdapter(new ChildSelectorAdapter(Child.getAll(), getActivity(), this));
    }

    @Override
    public void onDateSelected(Date date) {
        mChoreDate.setText(DateFormatter.formatDate(date));
        validateInput();
    }

    @Override
    public void onChildSelected(Child child) {
        mChildSelected = child;
        validateInput();
    }

    @OnClick(R.id.btn_create_chore)
    public void  onCreateChoreSelected() {
        float middle = - ((View) mButton.getParent()).getHeight() / 2;
        float buttonHeight = mButton.getHeight() / 2;
        float translation = middle + buttonHeight;

        if (mButton.getProgress() == 0) {
            mButton.setIndeterminateProgressMode(true);
            mButton.setProgress(50);
            displayInput(false);
            mButton.animate().translationY(translation);
        } else if (mButton.getProgress() == 100) {
            mButton.setProgress(0);
            mButton.animate().translationY(1).withEndAction(new Runnable() {
                @Override
                public void run() {
                    displayInput(true);
                }
            });
        } else {
            mButton.setProgress(100);
        }
    }

    private void displayInput(boolean display) {
        int alphaValue = display ? 1 : 0;

        mLabelChoreSelection.animate().alpha(alphaValue);
        mLabelChoreDate.animate().alpha(alphaValue);
        mLabelChoreAmount.animate().alpha(alphaValue);
        mLabelChild.animate().alpha(alphaValue);
        mChoreZoneSpinner.animate().alpha(alphaValue);
        mChoreTypeSpinner.animate().alpha(alphaValue);
        mChoreDate.animate().alpha(alphaValue);
        mAmount.animate().alpha(alphaValue);
        mChildSelectorGridView.animate().alpha(alphaValue);
    }

    private void validateInput() {
        boolean enabled = false;
        float alphaValue = .3f;

        if (mChoreZone != null && mChoreType != null && mChildSelected != null) {
            enabled = true;
            alphaValue = 1;
        }

        // Hack!!! only way to pass the correct variable to the inner class without having to have
        // multiple end actions
        final boolean finalEnabled = enabled;

        mButton.animate().alpha(alphaValue).withEndAction(new Runnable() {
            @Override
            public void run() {
                mButton.setEnabled(finalEnabled);
            }
        });
    }
}
