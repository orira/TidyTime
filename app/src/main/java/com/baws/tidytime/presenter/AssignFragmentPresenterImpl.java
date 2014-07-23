package com.baws.tidytime.presenter;

import android.content.Context;
import android.os.Handler;

import com.baws.tidytime.asynctask.CreateChoreTask;
import com.baws.tidytime.event.ChoreCreatedEvent;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.model.Chore;
import com.baws.tidytime.module.BusModule;
import com.baws.tidytime.view.AssignView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import util.AnimationLength;

/**
 * Created by wadereweti on 22/07/14.
 */
public class AssignFragmentPresenterImpl extends AbstractPresenter implements AssignFragmentPresenter {

    @Inject
    Bus mBus;

    Context mContext;

    private AssignView mFragmentView;
    private Chore mCreatedChore;

    public AssignFragmentPresenterImpl(AssignView fragmentView, Context context) {
        super();
        mBus.register(this);
        mContext = context;
        mFragmentView = fragmentView;
        initialiseView();
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new BusModule());
    }

    private void initialiseView() {
        mFragmentView.initialiseChoreSpinners();
        mFragmentView.initialiseDate();
        mFragmentView.initialiseIncentive();
        mFragmentView.initialiseChildSelector();
    }

    @Override
    public void onChoreZoneSelected(String zone, int zonePosition) {
        boolean display = zone != null;
        mFragmentView.setChoreZone(zone);
        mFragmentView.setChoreTypeAdapter(zonePosition);
        mFragmentView.displayChoreTypeSpinner(display);
    }

    @Override
    public void onChoreTypeSelected(String type) {
        mFragmentView.setChoreType(type);
    }

    @Override
    public void validateInput(String choreZone, String choreType, Child childSelected) {
        boolean enabled = false;

        if (choreZone != null && choreType != null && childSelected != null) {
            enabled = true;
        }

        mFragmentView.enableButton(enabled);
    }

    @Override
    public void onCreateChoreRequested(int progress, Child child, String choreType, String choreDate) {
        mFragmentView.displayLoadingState();
        mFragmentView.displayInput(false);
        mFragmentView.enableInput(false);

        new CreateChoreTask(child).execute(choreType, choreDate);
    }

    @Override
    public void onButtonReturnedToDefaultState() {
        mFragmentView.setButtonProgress(0);
        mFragmentView.resetZoneSpinner();
        mFragmentView.initialiseDate();
        mFragmentView.initialiseIncentive();
        mFragmentView.initialiseChildSelector();
        mFragmentView.displayInput(true);
        mFragmentView.enableInput(true);
    }

    @Subscribe
    public void answerAvailable(ChoreCreatedEvent event) {
        int progress = event.isCreated() ? 100 : -1;
        mCreatedChore = event.getChore();
        mFragmentView.setButtonProgress(progress);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mFragmentView.restoreButtonPosition();
            }
        }, AnimationLength.LONG);
    }
}
