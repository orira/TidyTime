package com.baws.tidytime.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.LruCache;
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
import com.baws.tidytime.event.ChildCreatedEvent;
import com.baws.tidytime.fragment.dialog.CalendarDialogFragment;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.module.CreateChoreModule;
import com.baws.tidytime.presenter.CreateChorePresenter;
import com.baws.tidytime.util.Constants;
import com.baws.tidytime.util.DateUtil;
import com.baws.tidytime.util.DimensionUtil;
import com.baws.tidytime.view.AvatarView;
import com.baws.tidytime.view.ChildSelectorView;
import com.baws.tidytime.view.CreateChoreView;
import com.baws.tidytime.view.DateView;
import com.baws.tidytime.widget.ChoreTypeSpinner;
import com.baws.tidytime.widget.ChoreZoneSpinner;
import com.baws.tidytime.widget.CircularImageView;
import com.baws.tidytime.widget.RobotoTextView;
import com.dd.CircularProgressButton;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
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
public class CreateChoreFragment extends AbstractFragment implements CreateChoreView, DateView, AvatarView {

    private static final String TAG = "AssignFragment";

    private String mChoreZone;
    private String mChoreType;
    private List<Child> mChildrenSelected = new ArrayList<Child>();
    private List<Integer> mChildrenSelectedViewIds = new ArrayList<Integer>();

    private int mDefaultViewValue;
    private int mHiddenViewValue;

    @Inject CreateChorePresenter mPresenter;
    @Inject LruCache<String, Bitmap> mBitmapCache;

    @InjectView(R.id.container_assign_fragment) RelativeLayout mContainer;
    @InjectView(R.id.label_chore_selection) RobotoTextView mLabelChoreSelection;
    @InjectView(R.id.label_chore_date) RobotoTextView mLabelChoreDate;
    @InjectView(R.id.label_chore_amount) RobotoTextView mLabelChoreAmount;
    @InjectView(R.id.label_child) RobotoTextView mLabelChild;
    @InjectView(R.id.sp_chore_zone) ChoreZoneSpinner mChoreZoneSpinner;
    @InjectView(R.id.sp_chore_type) ChoreTypeSpinner mChoreTypeSpinner;
    @InjectView(R.id.chore_date) EditText mChoreDate;
    @InjectView(R.id.sp_amount) Spinner mAmount;
    @InjectView(R.id.gv_child_selector) GridLayout mChildSelectorGridView;
    @InjectView(R.id.btn_create_chore) CircularProgressButton mButton;

    public static CreateChoreFragment get() {
        CreateChoreFragment fragment = new CreateChoreFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new CreateChoreModule(this));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.saveState(outState, (ArrayList) mChildrenSelectedViewIds, mChoreZoneSpinner.getSelectedItemPosition(), mChoreTypeSpinner.getSelectedItemPosition(), mChoreDate.getText().toString(), mAmount.getSelectedItemPosition());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_chore, container, false);
        ButterKnife.inject(this, view);

        mDefaultViewValue = getResources().getInteger(R.integer.default_view_value);
        mHiddenViewValue = getResources().getInteger(R.integer.hide_view_value);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.onRestoreState(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
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
                calendar.setDateView(CreateChoreFragment.this);
                calendar.show(getActivity().getSupportFragmentManager(), getString(R.string.title_calendar_dialog_fragment));
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

            if (getBitmapFromCache(child.getId().toString()) == null) {
                if (child.profilePicture != null) {
                    BitmapTask bitmapTask = new BitmapTask(avatar, mBitmapCache);
                    bitmapTask.execute(child.profilePicture, child.getId().toString());
                } else {
                    avatar.setImageResource(R.drawable.image_placeholder);
                }
            } else {
                avatar.setImageBitmap(getBitmapFromCache(child.getId().toString()));
            }

            int color = Color.parseColor(child.profileColour);
            int translucentColor = Color.parseColor(child.profileColourTranslucent);
            avatar.setBorderColor(color);
            avatar.setSelectorColor(translucentColor);
            avatar.setSelectorStrokeColor(color);

            if (child.profilePicture != null) {
                BitmapTask bitmapTask = new BitmapTask(avatar, mBitmapCache);
                bitmapTask.execute(child.profilePicture, child.getId().toString());
            } else {
                avatar.setImageResource(R.drawable.image_placeholder);
            }

            avatar.setTag(false);
            avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    boolean selected = (Boolean) view.getTag();
                    float scaleFactor = selected ? mDefaultViewValue: DimensionUtil.getFloat(R.dimen.scale_enabled, getResources());
                    float alphaValue = selected ? DimensionUtil.getFloat(R.dimen.default_image_opacity, getResources()) : mDefaultViewValue;

                    Child currentChild = (Child) ((View) view.getParent()).getTag();
                    Integer selectedViewId = currentChild.getId().intValue();

                    if (selected) {
                        mChildrenSelected.remove(currentChild);
                        mChildrenSelectedViewIds.remove(selectedViewId);
                    } else {
                        mChildrenSelected.add(currentChild);
                        mChildrenSelectedViewIds.add(selectedViewId);
                    }

                    view.setTag(!selected);
                    ((View) view.getParent()).animate().scaleX(scaleFactor).scaleY(scaleFactor).alpha(alphaValue);
                    //view.animate().scaleX(scaleFactor).scaleY(scaleFactor).alpha(alphaValue);

                    validateInput();
                }
            });

            // Add id programatically for state refresh on config change
            view.setId(child.getId().intValue());

            mChildSelectorGridView.addView(view);
        }
    }

    @Override
    public void restoreChildViewState(List<Integer> selectedChildrenViewIds) {
        mChildrenSelectedViewIds = selectedChildrenViewIds;

        for (Integer selectedChildId : selectedChildrenViewIds) {
            View view = mChildSelectorGridView.findViewById(selectedChildId);
            mChildrenSelected.add((Child) view.getTag());

            View childView = ((ViewGroup) view).getChildAt(0);
            childView.setTag(true);

            float scaleFactor = DimensionUtil.getFloat(R.dimen.scale_enabled, getResources());
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setAlpha(mDefaultViewValue);
        }
    }

    @Override
    public void restoreChoreSpinnerState(int selectedChoreZone, final int selectedChoreType) {
        mChoreZoneSpinner.setSelection(selectedChoreZone);

        // Add a delay to allow animation layout to run
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mChoreTypeSpinner.setSelection(selectedChoreType);
            }
        }, getResources().getInteger(R.integer.animation_duration_standard));
    }

    @Override
    public void restoreChoreDateState(String selectedChoreDate) {
        mChoreDate.setText(selectedChoreDate);
    }

    @Override
    public void restoreAmountState(int choreAmount) {
        mAmount.setSelection(choreAmount);
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
        validateInput();
    }

    @Override
    public void onChoreTypeSelected(String type) {
        mChoreType = type;
        validateInput();
    }

    @Override
    public void onDateSelected(Date date) {
        mChoreDate.setText(DateUtil.formatDate(date));
        validateInput();
    }

    @Override
    public void enableButton(final boolean enabled) {
        float alphaValue = enabled ? mDefaultViewValue : DimensionUtil.getFloat(R.dimen.disabled_element_opacity, getResources());

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
        int alphaValue = display ? mDefaultViewValue : mHiddenViewValue;

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
        if (mButton == null) {
            return;
        }

        mButton.animate().translationY(mDefaultViewValue).withEndAction(new Runnable() {
            @Override
            public void run() {
                mPresenter.onButtonReturnedToDefaultState();
            }
        });
    }

    @Override
    public void resetZoneSpinner() {
        mChoreZoneSpinner.setSelection(mHiddenViewValue);
    }

    @Override
    public void validateInput() {
        mPresenter.validateInput(mChoreZone, mChoreType, mChildrenSelected);
    }

    @Override
    public void resetInput() {
        mChildrenSelected.clear();
    }

    @OnClick(R.id.btn_create_chore)
    public void onCreateChoreSelected() {
        mPresenter.onCreateChoreRequested(mButton.getProgress(), mChildrenSelected, mChoreType, mChoreDate.getText().toString());
    }

    @Override
    public Bitmap getBitmapFromCache(String key) {
        return mBitmapCache.get(key);
    }

    /**
     * We subscribe here as well, because a user can navigate back from the create Activity
     * while the child is still being created, thereby making things nice and responsive
     */
    @Subscribe
    public void onChildCreated(ChildCreatedEvent event) {
        if (event.getChild() != null) {
            initialiseChildSelector();
        }
    }
}