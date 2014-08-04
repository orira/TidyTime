package com.baws.tidytime.view;

/**
 * Created by wadereweti on 22/07/14.
 */
public interface CreateChoreView extends PresenterView {
    void initialiseChoreSpinners();
    void initialiseDate();
    void initialiseIncentive();
    void initialiseChildSelector();
    void restoreChildViewState(int selectedChildId);
    void restoreChoreSpinnerState(int selectedChoreZone, int selectedChoreType);
    void restoreChoreDateState(String selectedChoreDate);
    void restoreAmountState(int choreAmount);
    void displayChoreTypeSpinner(boolean display);
    void enableButton(final boolean enabled);
    void displayLoadingState();
    void displayInput(boolean display);
    void enableInput(boolean enable);
    void setButtonProgress(int progress);
    void onChoreZoneSelected(String zone, int zonePosition);
    void setChoreTypeAdapter(int zonePosition);
    void onChoreTypeSelected(String zone);
    void restoreButtonPosition();
    void resetZoneSpinner();
    void validateInput();
}
