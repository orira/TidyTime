package com.baws.tidytime.presenter;

import android.os.Handler;

import com.baws.tidytime.asynctask.CreateChoreTask;
import com.baws.tidytime.event.ChoreCreatedEvent;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.util.AnimationLength;
import com.baws.tidytime.view.AssignView;
import com.baws.tidytime.view.PresenterView;
import com.squareup.otto.Subscribe;

/**
 * Created by wadereweti on 22/07/14.
 */
public class AssignChorePresenterImpl extends AbstractPresenter implements AssignFragmentPresenter {

    private AssignView mView;

    public AssignChorePresenterImpl(AssignView fragmentView) {
        super();
        mView = fragmentView;
    }

    @Override
    public void onResume(PresenterView view) {
        mView = (AssignView) view;
    }

    @Override
    public void initialiseView() {
        mView.initialiseChoreSpinners();
        mView.initialiseDate();
        mView.initialiseIncentive();
        mView.initialiseChildSelector();
    }

    @Override
    public void onChoreZoneSelected(String zone, int zonePosition) {
        boolean display = zone != null;
        mView.setChoreTypeAdapter(zonePosition);
        mView.displayChoreTypeSpinner(display);
    }

    @Override
    public void validateInput(String choreZone, String choreType, Child childSelected) {
        boolean enabled = false;

        if (choreZone != null && choreType != null && childSelected != null) {
            enabled = true;
        }

        mView.enableButton(enabled);
    }

    @Override
    public void onCreateChoreRequested(int progress, Child child, String choreType, String choreDate) {
        if (progress == 0) {
            mView.displayLoadingState();
            mView.displayInput(false);
            mView.enableInput(false);

            new CreateChoreTask(child).execute(choreType, choreDate);
        }
    }

    @Override
    public void onButtonReturnedToDefaultState() {
        mView.setButtonProgress(0);
        mView.resetZoneSpinner();
        mView.initialiseDate();
        mView.initialiseIncentive();
        mView.initialiseChildSelector();
        mView.displayInput(true);
        mView.enableInput(true);
    }

    @Subscribe
    public void answerAvailable(ChoreCreatedEvent event) {
        int progress = event.getChore() != null ? 100 : -1;
        mView.setButtonProgress(progress);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mView.restoreButtonPosition();
            }
        }, AnimationLength.LONG);
    }
}
