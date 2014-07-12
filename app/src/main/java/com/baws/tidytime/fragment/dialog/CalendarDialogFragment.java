package com.baws.tidytime.fragment.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baws.tidytime.R;
import com.baws.tidytime.view.DateView;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by wadereweti on 12/07/14.
 */
public class CalendarDialogFragment extends DialogFragment {

    @InjectView(R.id.calendar_view)
    CalendarPickerView mCalendarView;

    private DateView mDateView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_calendar, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().setTitle(getString(R.string.select_date));

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.MONTH, 1);

        Date today = new Date();
        mCalendarView.init(today, nextYear.getTime()).withSelectedDate(today).inMode(CalendarPickerView.SelectionMode.SINGLE);

        mCalendarView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                mDateView.onDateSelected(date);
                CalendarDialogFragment.this.dismiss();
            }

            @Override
            public void onDateUnselected(Date date) {}
        });
    }

    public void setDateView(DateView dateView) {
        mDateView = dateView;
    }
}
