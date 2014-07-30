package com.baws.tidytime.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.baws.tidytime.activity.CreateChildActivity;
import com.baws.tidytime.asynctask.CreateChildTask;
import com.baws.tidytime.event.ChildCreatedEvent;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.service.ImageService;
import com.baws.tidytime.service.ImageServiceImpl;
import com.baws.tidytime.view.CreateChildView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by wadereweti on 25/07/14.
 */
public class CreateChildPresenterImpl extends AbstractPresenter implements CreateChildPresenter {

    private static final String TAG = "CreateChildPresenterImpl";

    private CreateChildView mView;
    private Bitmap mBitmap;
    private int mOrientation;

    public CreateChildPresenterImpl(CreateChildView view) {
        mView = view;
    }

    @Override
    public void initialise() {
        mView.initialiseActionBar();
        mView.initialiseInput();
    }

    @Override
    public void onImageReturned(Bitmap bitmap, int orientation) {
        mBitmap = bitmap;
        mOrientation = orientation;
    }

    @Override
    public void createChildRequest(String name) {
        new CreateChildTask(mBitmap, mOrientation).execute(name);
    }

    @Subscribe
    public void onChildCreated(ChildCreatedEvent event) {
        if (event.getChild() != null) {
            mView.onChildCreated();
        }
    }

}
