package com.baws.tidytime.presenter;

import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.Spinner;

import com.baws.tidytime.dto.ChoreDto;
import com.baws.tidytime.event.ChoreCreatedEvent;
import com.baws.tidytime.event.RefreshChoresEvent;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.service.ChoreService;
import com.baws.tidytime.util.AnimationLength;
import com.baws.tidytime.util.Constants;
import com.baws.tidytime.view.CreateChoreView;
import com.baws.tidytime.widget.ChoreTypeSpinner;
import com.baws.tidytime.widget.ChoreZoneSpinner;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wadereweti on 22/07/14.
 */
public class CreateChorePresenterImpl extends AbstractPresenter implements CreateChorePresenter {

    private static final String SELECTED_CHILD = "selectedChild";
    private static final String SELECTED_CHORE_ZONE = "selectedZone";
    private static final String SELECTED_CHORE_TYPE = "selectedType";
    private static final String SELECTED_CHORE_DATE = "selectedDate";
    private static final String SELECTED_CHORE_AMOUNT = "selectedAmount";

    private final CreateChoreView mView;
    private final ChoreService mService;

    // Config Change variables
    private int mSelectedChildViewId = Constants.DEFAULT_VALUE;
    private int mSelectedChoreZone = Constants.DEFAULT_VALUE;
    private int mSelectedChoreType = Constants.DEFAULT_VALUE;
    private String mSelectedChoreDate = null;
    private int mChoreAmount = Constants.DEFAULT_VALUE;

    public CreateChorePresenterImpl(Bus bus, CreateChoreView view, ChoreService service) {
        super(bus);
        mView = view;
        mService = service;
    }

    @Override
    public void saveState(Bundle outState, int childSelectedViewId, ChoreZoneSpinner choreZoneSpinner, ChoreTypeSpinner choreTypeSpinner, EditText choreDate, Spinner amount) {
        outState.putInt(SELECTED_CHILD, childSelectedViewId);
        outState.putInt(SELECTED_CHORE_ZONE, choreZoneSpinner.getSelectedItemPosition());
        outState.putInt(SELECTED_CHORE_TYPE, choreTypeSpinner.getSelectedItemPosition());
        outState.putString(SELECTED_CHORE_DATE, choreDate.getText().toString());
        outState.putInt(SELECTED_CHORE_AMOUNT, amount.getSelectedItemPosition());
    }

    @Override
    public void onRestoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mSelectedChildViewId = savedInstanceState.getInt(SELECTED_CHILD);
            mSelectedChoreZone = savedInstanceState.getInt(SELECTED_CHORE_ZONE);
            mSelectedChoreType = savedInstanceState.getInt(SELECTED_CHORE_TYPE);
            mSelectedChoreDate = savedInstanceState.getString(SELECTED_CHORE_DATE);
            mChoreAmount = savedInstanceState.getInt(SELECTED_CHORE_AMOUNT);
        }
    }

    @Override
    public void onResume() {
        if (mService.isWorking()) {
            mView.displayLoadingState();
        } else {
            mView.initialiseChoreSpinners();
            mView.initialiseDate();
            mView.initialiseIncentive();
            mView.initialiseChildSelector();

            if (mSelectedChildViewId != Constants.DEFAULT_VALUE) {
                mView.restoreChildViewState(mSelectedChildViewId);
            }

            if (mSelectedChoreZone != Constants.DEFAULT_VALUE) {
                mView.restoreChoreSpinnerState(mSelectedChoreZone, mSelectedChoreType);
            }

            if (mSelectedChoreDate != null) {
                 mView.restoreChoreDateState(mSelectedChoreDate);
            }

            if (mChoreAmount != Constants.DEFAULT_VALUE) {
                mView.restoreAmountState(mChoreAmount);
            }

            mView.validateInput();
        }
    }

    @Override
    public void onChoreZoneSelected(String zone, int zonePosition) {
        boolean display = zone != null;
        mView.setChoreTypeAdapter(zonePosition);
        mView.displayChoreTypeSpinner(display);
    }

    @Override
    public void validateInput(String choreZone, String choreType, int childrenSelected) {
        boolean enabled = false;

        if (choreZone != null && choreType != null && childrenSelected > 0) {
            enabled = true;
        }

        mView.enableButton(enabled);
    }

    @Override
    public void onCreateChoreRequested(int progress, Map<Long, Child> selectedChildren, String choreType, String choreDate) {
        if (progress == 0) {
            mView.displayLoadingState();
            mView.displayInput(false);
            mView.enableInput(false);

            List<ChoreDto> dtos = new ArrayList<ChoreDto>();
            for (Map.Entry<Long, Child> entry : selectedChildren.entrySet()) {
                dtos.add(new ChoreDto(choreDate, choreType, entry.getValue()));
            }

            mService.createChore(dtos);
        }
    }

    @Override
    public void onButtonReturnedToDefaultState() {
        mView.resetInput();
        mView.setButtonProgress(0);
        mView.resetZoneSpinner();
        mView.initialiseDate();
        mView.initialiseIncentive();
        mView.initialiseChildSelector();
        mView.displayInput(true);
        mView.enableInput(true);

        mBus.post(new RefreshChoresEvent(true));
    }


    @Subscribe
    public void onChoreCreated(ChoreCreatedEvent event) {
        int progress = event.isCreated() ? 100 : Constants.DEFAULT_VALUE;
        mView.setButtonProgress(progress);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mView.restoreButtonPosition();
            }
        }, AnimationLength.LONG);
    }
}