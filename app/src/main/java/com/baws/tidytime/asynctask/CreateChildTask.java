package com.baws.tidytime.asynctask;

import android.graphics.Bitmap;

import com.baws.tidytime.event.ChildCreatedEvent;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.module.ServiceModule;
import com.baws.tidytime.service.ImageService;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by wadereweti on 29/07/14.
 */
public class CreateChildTask extends AbstractTask<String, Void, Child> {

    private final Bitmap mBitmap;
    private final int mOrientation;

    @Inject
    ImageService mService;

    public CreateChildTask(Bitmap bitmap, int orientation) {
        super();
        mBitmap = bitmap;
        mOrientation = orientation;
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new ServiceModule());
    }

    @Override
    protected Child doInBackground(String... strings) {

        Child child = Child.get();
        child.firstName = strings[0];
        child.profilePicture = mService.saveImage(mBitmap, mOrientation, child);
        child.profilePictureOrientation = mOrientation;
        child.save();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return child;
    }

    @Override
    protected void onPostExecute(Child child) {
        super.onPostExecute(child);

        mBus.post(new ChildCreatedEvent(child));
    }
}
