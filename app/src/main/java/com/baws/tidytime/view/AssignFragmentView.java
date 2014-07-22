package com.baws.tidytime.view;

/**
 * Created by wadereweti on 22/07/14.
 */
public interface AssignFragmentView {
    void initialiseChoreZoneAdapter();
    void initialiseDate();
    void initialiseIncentive();
    void initialiseChildSelector();
    void setZone(String zone);
    void displayChoreTypeSpinner(boolean display);
    void enableButton(final boolean enabled);
    void displayLoadingState();
    void displayInput(boolean display);
    void enableInput(boolean enable);
    void setButtonProgress(int progress);
    void restoreButtonPosition();
    void onChoreZoneSelected(String zone);

}
