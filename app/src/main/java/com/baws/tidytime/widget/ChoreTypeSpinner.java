package com.baws.tidytime.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.baws.tidytime.R;
import com.baws.tidytime.view.CreateChoreView;

/**
 * Created by wadereweti on 22/07/14.
 */
public class ChoreTypeSpinner extends Spinner implements SpinnerView{

    public ChoreTypeSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAdapter(0);
    }

    public void setCallback(CreateChoreView assignFragmentView) {
        setOnItemSelectedListener(new TypeSelector(assignFragmentView));
    }

    @Override
    public void setAdapter(int postion) {
        ArrayAdapter<CharSequence> zoneAdapter = ArrayAdapter.createFromResource(getContext(), getChoresArray(postion), R.layout.spinner_item);
        zoneAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        setAdapter(zoneAdapter);
    }

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
            default:
                choresArray = R.array.bedroom_chores_array;
                break;
        }

        return choresArray;
    }

    private class TypeSelector implements OnItemSelectedListener {

        private CreateChoreView mAssignFragmentView;

        private TypeSelector(CreateChoreView assignFragmentView) {
            mAssignFragmentView = assignFragmentView;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            String type = ((TextView) view).getText().toString();
            mAssignFragmentView.onChoreTypeSelected(type);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {}
    }
}
