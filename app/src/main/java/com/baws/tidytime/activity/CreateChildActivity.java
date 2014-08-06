package com.baws.tidytime.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baws.tidytime.R;
import com.baws.tidytime.module.ActivityModule;
import com.baws.tidytime.module.CreateChildModule;
import com.baws.tidytime.presenter.CreateChildPresenter;
import com.baws.tidytime.util.Constants;
import com.baws.tidytime.view.CreateChildView;
import com.baws.tidytime.widget.CircularImageView;
import com.baws.tidytime.widget.RobotoTextView;
import com.iangclifton.android.floatlabel.FloatLabel;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Raukawa on 7/24/2014.
 */
public class CreateChildActivity extends AbstractActivity implements CreateChildView {

    private static final String TAG = "CreateChildActivity";

    private boolean mActionItemEnabled = true;
    private ActionMode mActionMode;

    @Inject CreateChildPresenter mPresenter;

    @InjectView(R.id.tv_add_photo) RobotoTextView mLabelAddPhoto;
    @InjectView(R.id.iv_profile_picture) CircularImageView mProfilePicture;
    @InjectView(R.id.fl_enter_name) FloatLabel mNameEditText;
    @InjectView(R.id.pb_create_child) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_child);
        ButterKnife.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_child, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                reverseActivityAnimation();
                return true;
            case R.id.action_add_child:
                mPresenter.createChildRequest(mNameEditText.getEditText().getText().toString());
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
        super.setTitle(getString(R.string.create_child));
        getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
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
    public void onInvalidInput() {
        Toast.makeText(this, getString(R.string.error_no_details_entered), Toast.LENGTH_LONG).show();
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
            mActionMode = CreateChildActivity.this.startActionMode(mCallBack);
        }
    }

    @Override
    public void disableCreate() {
        if (mActionMode != null) {
            mActionMode.finish();
        }
    }

    /*@Override
    public void disableActionItem() {
        mActionItemEnabled = false;
        invalidateOptionsMenu();
    }*/

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
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.create_child, menu);

            MenuItem item = menu.getItem(0);

            Log.e(TAG, "onCreateActionMode");

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            Log.e(TAG, "onPrepareActionMode");
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            Log.e(TAG, "onActionItemClicked");
            /*switch (menuItem.getItemId()) {
                case R.id.action_add_child:
                    mPresenter.createChildRequest(mNameEditText.getEditText().getText().toString());
                    break;
            }*/

            //mPresenter.createChildRequest(mNameEditText.getEditText().getText().toString());

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            Log.e(TAG, "onDestroyActionMode");
            mActionMode = null;
        }
    };
}