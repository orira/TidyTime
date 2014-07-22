package com.baws.tidytime.view;

import android.widget.ArrayAdapter;

/**
 * Created by wadereweti on 22/07/14.
 */
public interface AssignFragmentView {
    void initialiseChoreSpinners();
    void initialiseDate();
    void initialiseIncentive();
    void initialiseChildSelector();
    void displayChoreTypeSpinner(boolean display);
    void enableButton(final boolean enabled);
    void displayLoadingState();
    void displayInput(boolean display);
    void enableInput(boolean enable);
    void setButtonProgress(int progress);
    void restoreButtonPosition();
    void onChoreZoneSelected(String zone, int zonePosition);
    void setChoreZone(String zone);
    void setChoreTypeAdapter(int zonePosition);
    void onChoreTypeSelected(String zone);
    void setChoreType(String zone);

}
