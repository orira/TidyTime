package com.baws.tidytime.presenter;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import com.baws.tidytime.model.Child;
import com.baws.tidytime.widget.ChoreTypeSpinner;
import com.baws.tidytime.widget.ChoreZoneSpinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wadereweti on 22/07/14.
 */
public interface CreateChorePresenter extends Presenter {
    void saveState(Bundle outState, ArrayList childSelectedViewId, int choreZoneSpinner, int choreTypeSpinner, String choreDate, int amount);

    void onRestoreState(Bundle savedInstanceState);
    void onChoreZoneSelected(String zone, int zonePosition);

    void validateInput(String mChoreZone, String mChoreType, List<Child> childrenSelected);

    void onCreateChoreRequested(int progress, List<Child> childrenSelected, String mChoreType, String choreDate);
    void onButtonReturnedToDefaultState();


}
