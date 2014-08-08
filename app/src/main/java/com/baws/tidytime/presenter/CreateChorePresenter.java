package com.baws.tidytime.presenter;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import com.baws.tidytime.model.Child;
import com.baws.tidytime.widget.ChoreTypeSpinner;
import com.baws.tidytime.widget.ChoreZoneSpinner;

import java.util.Map;

/**
 * Created by wadereweti on 22/07/14.
 */
public interface CreateChorePresenter extends Presenter {
    void saveState(Bundle outState, int childSelectedViewId, ChoreZoneSpinner choreZoneSpinner, ChoreTypeSpinner choreTypeSpinner, EditText choreDate, Spinner amount);
    void onRestoreState(Bundle savedInstanceState);
    void onChoreZoneSelected(String zone, int zonePosition);
    void validateInput(String choreZone, String choreType, int childrenSelected);
    void onCreateChoreRequested(int progress, Map<Long, Child> mSelectedChildren, String mChoreType, String choreDate);
    void onButtonReturnedToDefaultState();

}
