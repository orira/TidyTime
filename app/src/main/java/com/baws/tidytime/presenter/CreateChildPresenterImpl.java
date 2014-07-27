package com.baws.tidytime.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.baws.tidytime.activity.CreateChildActivity;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.service.ImageService;
import com.baws.tidytime.service.ImageServiceImpl;
import com.baws.tidytime.view.CreateChildView;

import java.util.List;

/**
 * Created by wadereweti on 25/07/14.
 */
public class CreateChildPresenterImpl implements CreateChildPresenter {

    private static final String TAG = "CreateChildPresenterImpl";
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 100;

    private CreateChildView mView;
    private ImageService mImageService;
    private Bitmap mBitmap;
    private int mOrientation;

    public CreateChildPresenterImpl(CreateChildView view) {
        mView = view;
        mImageService = new ImageServiceImpl((CreateChildActivity) view);
    }

    @Override
    public void onImageReturned(Bitmap bitmap, int orientation) {
        mBitmap = bitmap;
        mOrientation = orientation;
    }

    @Override
    public void createChildRequest(String name) {
        Child child = Child.get();
        child.firstName = name;
        child.profilePicture = mImageService.saveImage(mBitmap, mOrientation, child);
        child.profilePictureOrientation = mOrientation;
        child.save();

        List<Child> childList = Child.getAll();
        Log.e(TAG, "foo");
    }
}
