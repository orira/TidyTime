package com.baws.tidytime.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import com.baws.tidytime.R;
import com.baws.tidytime.adapter.ChildSelectorAdapter;
import com.baws.tidytime.fragment.dialog.CalendarDialogFragment;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.module.AssignViewModule;
import com.baws.tidytime.presenter.AssignFragmentPresenter;
import com.baws.tidytime.view.AssignView;
import com.baws.tidytime.view.ChildSelectorView;
import com.baws.tidytime.view.DateView;
import com.baws.tidytime.view.MainView;
import com.baws.tidytime.widget.ChoreTypeSpinner;
import com.baws.tidytime.widget.ChoreZoneSpinner;
import com.baws.tidytime.widget.RobotoTextView;
import com.dd.CircularProgressButton;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import util.DateUtil;

/**
 * Created by wadereweti on 6/07/14.
 */
public class AssignFragment extends AbstractFragment implements AssignView, DateView, ChildSelectorView {

    private static final String TAG = "AssignFragment";

    private String mChoreZone;
    private String mChoreType;
    private Child mChildSelected;
    private MainView mMainView;

    @Inject
    AssignFragmentPresenter mPresenter;

    @InjectView(R.id.label_chore_selection)
    RobotoTextView mLabelChoreSelection;

    @InjectView(R.id.label_chore_date)
    RobotoTextView mLabelChoreDate;

    @InjectView(R.id.label_chore_amount)
    RobotoTextView mLabelChoreAmount;

    @InjectView(R.id.label_child)
    RobotoTextView mLabelChild;

    @InjectView(R.id.sp_chore_zone)
    ChoreZoneSpinner mChoreZoneSpinner;

    @InjectView(R.id.sp_chore_type)
    ChoreTypeSpinner mChoreTypeSpinner;

    @InjectView(R.id.chore_date)
    EditText mChoreDate;

    @InjectView(R.id.sp_amount)
    Spinner mAmount;

    @InjectView(R.id.gv_child_selector)
    GridView mChildSelectorGridView;

    @InjectView(R.id.btn_create_chore)
    CircularProgressButton mButton;

    public static AssignFragment get(MainView mainView) {
        AssignFragment fragment = new AssignFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        fragment.setMainView(mainView);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assign, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new AssignViewModule(this, getActivity()));
    }

    @Override
    public void initialiseChoreSpinners() {
        mChoreZoneSpinner.setCallback(this);
        mChoreTypeSpinner.setCallback(this);
    }

    @Override
    public void initialiseDate() {
        mChoreDate.setText(DateUtil.getToday(getActivity()));

        mChoreDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarDialogFragment calendar = new CalendarDialogFragment();
                calendar.setDateView(AssignFragment.this);
                calendar.show(getActivity().getSupportFragmentManager(), "calendarDialogFragment");
            }
        });
    }

    @Override
    public void initialiseIncentive() {
        ArrayAdapter<CharSequence> amountAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.chore_amount_array, R.layout.spinner_item);
        amountAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mAmount.setAdapter(amountAdapter);
    }

    @Override
    public void initialiseChildSelector() {
        mChildSelectorGridView.setAdapter(new ChildSelectorAdapter(Child.getAll(), getActivity(), this));
    }

    @Override
    public void setChoreZone(String zone) {
        mChoreZone = zone;
    }

    @Override
    public void setChoreTypeAdapter(int zonePosition) {
        mChoreTypeSpinner.setAdapter(zonePosition);
    }

    @Override
    public void displayChoreTypeSpinner(boolean display) {
        int visibility = display ? View.VISIBLE : View.GONE;

        mChoreTypeSpinner.setVisibility(visibility);
    }

    @Override
    public void onChoreZoneSelected(String zone, int zonePosition) {
        mPresenter.onChoreZoneSelected(zone, zonePosition);
    }

    @Override
    public void onChoreTypeSelected(String type) {
        mPresenter.onChoreTypeSelected(type);
    }

    @Override
    public void setChoreType(String type) {
        mChoreType = type;
    }

    @Override
    public void onDateSelected(Date date) {
        mChoreDate.setText(DateUtil.formatDate(date));
        mPresenter.validateInput(mChoreZone, mChoreType, mChildSelected);
    }

    @Override
    public void onChildSelected(Child child) {
        mChildSelected = child;
        mPresenter.validateInput(mChoreZone, mChoreType, mChildSelected);
    }

    @Override
    public void enableButton(final boolean enabled) {
        float alphaValue = enabled ? 1 : .3f;

        mButton.animate().alpha(alphaValue).withEndAction(new Runnable() {
            @Override
            public void run() {
                mButton.setEnabled(enabled);
            }
        });
    }

    @Override
    public void displayLoadingState() {
        float middle = - ((View) mButton.getParent()).getHeight() / 2;
        float buttonHeight = mButton.getHeight() / 2;
        float translation = middle + buttonHeight;

        mButton.setIndeterminateProgressMode(true);
        mButton.setProgress(50);
        mButton.animate().translationY(translation);
    }

    @Override
    public void displayInput(boolean display) {
        int alphaValue = display ? 1 : 0;

        mLabelChoreSelection.animate().alpha(alphaValue);
        mLabelChoreDate.animate().alpha(alphaValue);
        mLabelChoreAmount.animate().alpha(alphaValue);
        mLabelChild.animate().alpha(alphaValue);
        mChoreZoneSpinner.animate().alpha(alphaValue);
        mChoreTypeSpinner.animate().alpha(alphaValue);
        mChoreDate.animate().alpha(alphaValue);
        mAmount.animate().alpha(alphaValue);
        mChildSelectorGridView.animate().alpha(alphaValue);
    }

    @Override
    public void enableInput(boolean enable) {
        mChoreZoneSpinner.setEnabled(enable);
        mChoreTypeSpinner.setEnabled(enable);
        mChoreDate.setEnabled(enable);
        mAmount.setEnabled(enable);
        mChildSelectorGridView.setEnabled(enable);
        mButton.setEnabled(enable);
    }

    @Override
    public void setButtonProgress(int progress) {
        mButton.setProgress(progress);
    }

    @Override
    public void restoreButtonPosition() {
        mButton.animate().translationY(1).withEndAction(new Runnable() {
            @Override
            public void run() {
                mPresenter.onButtonReturnedToDefaultState();
            }
        });
    }

    @Override
    public void resetZoneSpinner() {
        mChoreZoneSpinner.setSelection(0);
    }

    @OnClick(R.id.btn_create_chore)
    public void onCreateChoreSelected() {
        mPresenter.onCreateChoreRequested(mButton.getProgress(), mChildSelected, mChoreType, mChoreDate.getText().toString());
    }

    public void setMainView(MainView mainView) {
        mMainView = mainView;
    }
}
