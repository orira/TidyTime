package com.baws.tidytime.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.baws.tidytime.R;
import com.baws.tidytime.view.AssignView;

/**
 * Created by wadereweti on 22/07/14.
 */
public class ChoreZoneSpinner extends Spinner {

    public ChoreZoneSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initisalise();
    }

    private void initisalise() {
        ArrayAdapter<CharSequence> zoneAdapter = ArrayAdapter.createFromResource(getContext(), R.array.chore_zone_array, R.layout.spinner_item);
        zoneAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        setAdapter(zoneAdapter);
    }

    public void setCallback(AssignView assignFragmentView) {
        setOnItemSelectedListener(new ZoneSelector(assignFragmentView));
    }

    private class ZoneSelector implements OnItemSelectedListener {

        private AssignView mAssignFragmentView;

        private ZoneSelector(AssignView assignFragmentView) {
            mAssignFragmentView = assignFragmentView;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            int choresArray = getChoresArray(position);

            // null on rotation, so check
            if (view != null) {
                String zone = choresArray != 0 ? ((TextView) view).getText().toString() : null;
                mAssignFragmentView.onChoreZoneSelected(zone, position);
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
    }
}
