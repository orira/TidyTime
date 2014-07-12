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
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.baws.tidytime.R;
import com.baws.tidytime.adapter.ChildSelectorAdapter;
import com.baws.tidytime.fragment.dialog.CalendarDialogFragment;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.view.DateView;
import com.baws.tidytime.widget.CircularImageView;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import util.DateFormatter;

/**
 * Created by wadereweti on 6/07/14.
 */
public class AssignFragment extends Fragment implements DateView {

    private static final String TAG = "AssingFragment";
    private static final float SCALE_FACTOR = 1.2f;

    @InjectView(R.id.sp_chore_zone)
    Spinner mChoreZoneSpinner;

    @InjectView(R.id.sp_chore_type)
    Spinner mChoreTypeSpinner;

    @InjectView(R.id.chore_date)
    EditText mChoreDate;

    @InjectView(R.id.sp_amount)
    Spinner mAmount;

    @InjectView(R.id.gv_child_selector)
    GridView mChildSelector;

    private View selectedView;

    private AdapterView.OnItemSelectedListener zoneSelector = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            int chores = 0;

            switch (position) {
                case 0:
                    chores = R.array.bedroom_chores_array;
                    break;
                case 1:
                    chores = R.array.bathroom_chores_array;
                    break;
                case 2:
                    chores = R.array.kitchen_chores_array;
                    break;
                case 3:
                    chores = R.array.lounge_chores_array;
                    break;
                case 4:
                    chores = R.array.washhouse_chores_array;
                    break;
                case 5:
                    chores = R.array.garage_chores_array;
                    break;
                case 6:
                    chores = R.array.outside_chores_array;
                    break;
            }

            ArrayAdapter<CharSequence> typeAdapter =
                    ArrayAdapter.createFromResource(getActivity(), chores, R.layout.spinner_item);
            typeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            mChoreTypeSpinner.setAdapter(typeAdapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {}
    };

    private AdapterView.OnItemSelectedListener typeSelector = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
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

        mChoreTypeSpinner.setOnItemSelectedListener(typeSelector);

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

        mChildSelector.setAdapter(new ChildSelectorAdapter(Child.getAll(), getActivity()));
        mChildSelector.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final ChildSelectorAdapter.ViewHolder viewHolder = (ChildSelectorAdapter.ViewHolder) view.getTag();

                if (selectedView != null) {
                    // Animate old view to default
                    selectedView.animate().scaleX(1).scaleY(1).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            ((ChildSelectorAdapter.ViewHolder) selectedView.getTag()).imageView.setBorderColor(getResources().getColor(R.color.primary));
                        }
                    });

                    // Set new view to selected
                    selectedView = view;
                } else {
                    selectedView = view;
                }

                view.animate().scaleX(SCALE_FACTOR).scaleY(SCALE_FACTOR).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder.imageView.setBorderColor(getResources().getColor(R.color.primary_accent));
                    }
                });
            }
        });
    }

    @Override
    public void onDateSelected(Date date) {
        mChoreDate.setText(DateFormatter.formatDate(date));
    }
}
