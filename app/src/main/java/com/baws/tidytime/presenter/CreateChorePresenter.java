package com.baws.tidytime.presenter;

import com.baws.tidytime.model.Child;

/**
 * Created by wadereweti on 22/07/14.
 */
public interface CreateChorePresenter extends Presenter {
    void onChoreZoneSelected(String zone, int zonePosition);
    void validateInput(String choreZone, String choreType, Child childSelected);
    void onCreateChoreRequested(int progress, Child child, String choreType, String choreDate);
    void onButtonReturnedToDefaultState();
}