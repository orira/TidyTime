package com.baws.tidytime.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.baws.tidytime.R;
import com.baws.tidytime.activity.CreateChildActivity;
import com.baws.tidytime.asynctask.CreateChildTask;
import com.baws.tidytime.dto.ChildDto;
import com.baws.tidytime.event.ChildCreatedEvent;
import com.baws.tidytime.service.ChildService;
import com.baws.tidytime.util.ImageUtil;
import com.baws.tidytime.view.CreateChildView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Created by wadereweti on 25/07/14.
 */
public class CreateChildPresenterImpl extends AbstractPresenter implements CreateChildPresenter {

    private static final String TAG = "CreateChildPresenterImpl";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_FILE = 0;

    private final CreateChildView mView;
    private final Context mContext;
    private final ChildService mService;

    private Bitmap mBitmap;
    private int mOrientation;

    public CreateChildPresenterImpl(Bus bus, CreateChildView view, Context context, ChildService service) {
        super(bus);
        mView = view;
        mContext = context;
        mService = service;
    }

    @Override
    public void onResume() {
        if (mService.isWorking()) {
            mView.displayCreationState();
        } else {
            mView.initialiseView();
            mView.initialiseActionBar();
            mView.initialiseInput();
        }
    }

    @Override
    public void photoRequested() {
        final String TITLE = mContext.getString(R.string.label_add_photo);
        final String TAKE_PHOTO = mContext.getString(R.string.label_take_photo);
        final String SELECT_PHOTO = mContext.getString(R.string.label_select_photo);
        final String CANCEL = mContext.getString(R.string.label_cancel);

        final CharSequence[] items = {TAKE_PHOTO, SELECT_PHOTO, CANCEL};

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(TITLE);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(TAKE_PHOTO)) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {
                        ((CreateChildActivity) mContext).startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                } else if (items[item].equals(SELECT_PHOTO)) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    ((CreateChildActivity) mContext).startActivityForResult(intent, REQUEST_IMAGE_FILE);
                } else if (items[item].equals(CANCEL)) {
                    dialog.dismiss();
                }
            }
        });

        mView.showDialog(builder);
    }

    @Override
    public void onImageReturned(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = null;
        int orientation = 0;

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                bitmap = (Bitmap) extras.get("data");
            } else if (requestCode == REQUEST_IMAGE_FILE) {
                try {
                    Uri imageUri = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), imageUri);
                    orientation = ImageUtil.getPhotoOrientation(imageUri);
                } catch (Exception exception) {
                    Log.e(TAG, "File not found, or io exception");
                }
            }
        }

        if (bitmap != null) {
            mBitmap = bitmap;
            mOrientation = orientation;
            mView.setProfileImage(bitmap);
        }
    }

    @Override
    public void createChildRequest(String name) {
        if (TextUtils.isEmpty(name)) {
            mView.onInvalidInput();
            return;
        }

        mView.disableActionItem();
        mView.displayCreationState();

        ChildDto dto = new ChildDto(mBitmap, mOrientation, name);
        mService.createChild(dto);
    }

    @Subscribe
    public void onChildCreated(ChildCreatedEvent event) {
        if (event.getChild() != null) {
            mView.onChildCreated();
        }
    }
}
