package com.baws.tidytime.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.baws.tidytime.R;
import com.baws.tidytime.asynctask.BitmapTask;
import com.baws.tidytime.fragment.dialog.CalendarDialogFragment;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.module.AssignChorePresenterModule;
import com.baws.tidytime.presenter.AssignFragmentPresenter;
import com.baws.tidytime.util.DateUtil;
import com.baws.tidytime.view.AssignView;
import com.baws.tidytime.view.ChildSelectorView;
import com.baws.tidytime.view.DateView;
import com.baws.tidytime.view.MainView;
import com.baws.tidytime.widget.ChoreTypeSpinner;
import com.baws.tidytime.widget.ChoreZoneSpinner;
import com.baws.tidytime.widget.CircularImageView;
import com.baws.tidytime.widget.RobotoTextView;
import com.dd.CircularProgressButton;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by wadereweti on 6/07/14.
 */
public class AssignChoreFragment extends AbstractFragment implements AssignView, DateView, ChildSelectorView {

    private static final String TAG = "AssignFragment";
    private static final float SCALE_SELECTED = 1.3f;
    private static final float SCALE_DEFAULT = 1;
    private static final String CHORE_STATE = "creatingChore";
    private static final String PRESENTER = "presenter";

    private String mChoreZone;
    private String mChoreType;
    private Child mChildSelected;
    private MainView mMainView;

    @Inject AssignFragmentPresenter mPresenter;
    @InjectView(R.id.container_assign_fragment) RelativeLayout mContainer;
    @InjectView(R.id.label_chore_selection) RobotoTextView mLabelChoreSelection;
    @InjectView(R.id.label_chore_date) RobotoTextView mLabelChoreDate;
    @InjectView(R.id.label_chore_amount) RobotoTextView mLabelChoreAmount;
    @InjectView(R.id.label_child) RobotoTextView mLabelChild;
    @InjectView(R.id.sp_chore_zone) ChoreZoneSpinner mChoreZoneSpinner;
    @InjectView(R.id.sp_chore_type) ChoreTypeSpinner mChoreTypeSpinner;
    @InjectView(R.id.chore_date) EditText mChoreDate;
    @InjectView(R.id.sp_amount) Spinner mAmount;
    //@InjectView(R.id.gv_child_selector) GridView mChildSelectorGridView;
    @InjectView(R.id.gv_child_selector) GridLayout mChildSelectorGridView;
    @InjectView(R.id.btn_create_chore) CircularProgressButton mButton;

    public static AssignChoreFragment get(MainView mainView) {
        AssignChoreFragment fragment = new AssignChoreFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.setMainView(mainView);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assign, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.initialiseView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(CHORE_STATE, mButton.getProgress());
        //outState.putParcelable(PRESENTER, (AssignFragmentPresenterImpl) mPresenter);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new AssignChorePresenterModule(this));
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
                calendar.setDateView(AssignChoreFragment.this);
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
        mChildSelectorGridView.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(getActivity());

        for (Child child : Child.getAll()) {
            View view = inflater.inflate(R.layout.gridview_child_selector, mContainer, false);

            RelativeLayout container = ButterKnife.findById(view, R.id.container_profile_picture);
            container.setTag(child);

            CircularImageView avatar = ButterKnife.findById(view, R.id.iv_profile_picture);
            BitmapTask bitmapTask = new BitmapTask(avatar);
            bitmapTask.execute(child.profilePicture);
            avatar.setTag(false);

            avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean selected = (Boolean) view.getTag();
                    float scaleFactor = selected ? SCALE_DEFAULT : SCALE_SELECTED;
                    float alpha = selected ? .6f : 1;

                    mChildSelected = selected ? null : (Child) ((View) view.getParent()).getTag();

                    view.setTag(!selected);
                    ((View) view.getParent()).animate().scaleX(scaleFactor).scaleY(scaleFactor).alpha(alpha);

                    mPresenter.validateInput(mChoreZone, mChoreType, mChildSelected);
                }
            });

            mChildSelectorGridView.addView(view);
        }
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
        mChoreZone = zone;
        mPresenter.onChoreZoneSelected(zone, zonePosition);
        mPresenter.validateInput(mChoreZone, mChoreType, mChildSelected);
    }

    @Override
    public void onChoreTypeSelected(String type) {
        mChoreType = type;
        mPresenter.validateInput(mChoreZone, mChoreType, mChildSelected);
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

        // new api breaks animation when disabled
        //mButton.setEnabled(enable);
    }

    @Override
    public void setButtonProgress(int progress) {
        if (mButton != null) {
            mButton.setProgress(progress);
        }
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