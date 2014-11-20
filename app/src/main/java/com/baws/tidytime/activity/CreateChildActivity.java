package com.baws.tidytime.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.baws.tidytime.R;
import com.baws.tidytime.module.ActivityModule;
import com.baws.tidytime.module.CreateChildModule;
import com.baws.tidytime.presenter.CreateChildPresenter;
import com.baws.tidytime.util.Constants;
import com.baws.tidytime.util.KeyboardUtil;
import com.baws.tidytime.view.CreateChildView;
import com.baws.tidytime.widget.RobotoTextView;
import com.iangclifton.android.floatlabel.FloatLabel;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Raukawa on 7/24/2014.
 */
public class CreateChildActivity extends AbstractActivity implements CreateChildView {

    private static final String TAG = "CreateChildActivity";

    private ActionMode mActionMode;
    private Context mCurrentContext;

    @Inject CreateChildPresenter mPresenter;

    @InjectView(R.id.tv_add_photo) RobotoTextView mLabelAddPhoto;
    @InjectView(R.id.iv_profile_picture) CircleImageView mProfilePicture;
    @InjectView(R.id.fl_enter_name) FloatLabel mNameEditText;
    @InjectView(R.id.pb_create_child) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_child);
        ButterKnife.inject(this);
        setLogo(null);

        mCurrentContext = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                reverseActivityAnimation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        reverseActivityAnimation();
    }

    protected List<Object> getModules() {
        return Arrays.<Object>asList(new ActivityModule(this), new CreateChildModule(this));
    }

    private void reverseActivityAnimation() {
        finish();
        overridePendingTransition(R.anim.scale_up_alpha, R.anim.slide_out_bottom);
    }

    @Override
    public void initialiseView() {
        mLabelAddPhoto.animate().alpha(Constants.VIEW_VISIBLE);
        mProfilePicture.animate().alpha(Constants.VIEW_VISIBLE);
        mNameEditText.animate().alpha(Constants.VIEW_VISIBLE);
    }

    @Override
    public void initialiseActionBar() {
        setTitle(getString(R.string.create_child));
    }

    @Override
    public void initialiseInput() {
        mNameEditText.setLabel(R.string.enter_name);
        mNameEditText.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                mPresenter.onNameEntered(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        mProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.photoRequested();
            }
        });
    }

    @Override
    public void showDialog(AlertDialog.Builder builder) {
        builder.show();
    }

    @Override
    public void setProfileImage(Bitmap bitmap) {
        mProfilePicture.setImageBitmap(bitmap);
    }

    @Override
    public void enableCreate() {
        if (mActionMode == null) {
            getActionBarToolBar().setVisibility(View.GONE);
            mActionMode = CreateChildActivity.this.startActionMode(mCallBack);

            int doneButtonId = Resources.getSystem().getIdentifier("action_mode_close_button", "id", "android");
            View doneButton = this.findViewById(doneButtonId);
            doneButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    KeyboardUtil.hideKeyboard(mCurrentContext, mNameEditText.getEditText());
                    mPresenter.createChildRequest(mNameEditText.getEditText().getText().toString());
                    mActionMode.finish();
                    getActionBarToolBar().setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void disableCreate() {
        if (mActionMode != null) {
            mActionMode.finish();
            getActionBarToolBar().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void displayCreationState() {
        mLabelAddPhoto.animate().alpha(Constants.VIEW_GONE);
        mProfilePicture.animate().alpha(Constants.VIEW_GONE);
        mNameEditText.animate().alpha(Constants.VIEW_GONE);
        mProgressBar.animate().alpha(Constants.VIEW_VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onImageReturned(requestCode, resultCode, data);
    }

    @Override
    public void onChildCreated() {
        reverseActivityAnimation();
    }

    private ActionMode.Callback mCallBack = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            // We don't need to inflate a menu as the default tick is all we need
            /*MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.create_child, menu);*/

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            mActionMode = null;
        }
    };
}