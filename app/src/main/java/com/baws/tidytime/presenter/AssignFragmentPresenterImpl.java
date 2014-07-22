package com.baws.tidytime.presenter;

import android.os.Handler;

import com.baws.tidytime.BusUtil;
import com.baws.tidytime.asynctask.CreateChoreTask;
import com.baws.tidytime.event.ChoreCreatedEvent;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.module.BusModule;
import com.baws.tidytime.view.AssignFragmentView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by wadereweti on 22/07/14.
 */
public class AssignFragmentPresenterImpl extends AbstractPresenter implements AssignFragmentPresenter {

    @Inject
    Bus mBus;

    private AssignFragmentView mFragmentView;

    public AssignFragmentPresenterImpl(AssignFragmentView fragmentView) {
        super();
        mBus.register(this);
        //BusUtil.get().register(this);
        mFragmentView = fragmentView;
        initialiseView();
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new BusModule());
    }

    private void initialiseView() {
        mFragmentView.initialiseChoreZoneAdapter();
        mFragmentView.initialiseDate();
        mFragmentView.initialiseIncentive();
        mFragmentView.initialiseChildSelector();
    }

    @Override
    public void onChoreZoneSelected(String zone) {
        boolean display = zone != null;
        mFragmentView.setZone(zone);
        mFragmentView.displayChoreTypeSpinner(display);
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
        mFragmentView.initialiseChoreZoneAdapter();
        mFragmentView.initialiseDate();
        mFragmentView.initialiseIncentive();
        mFragmentView.initialiseChildSelector();
        mFragmentView.displayInput(true);
        mFragmentView.enableInput(true);
    }

    @Subscribe
    public void answerAvailable(ChoreCreatedEvent event) {
        int progress = event.isCreated() ? 100 : -1;
        mFragmentView.setButtonProgress(progress);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mFragmentView.restoreButtonPosition();
            }
        }, 600);
    }
}
