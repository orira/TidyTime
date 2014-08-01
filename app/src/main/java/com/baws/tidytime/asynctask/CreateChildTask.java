package com.baws.tidytime.asynctask;

import android.os.AsyncTask;

import com.baws.tidytime.dto.ChildDto;
import com.baws.tidytime.event.ChildCreatedEvent;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.service.ImageService;
import com.squareup.otto.Bus;

/**
 * Created by wadereweti on 29/07/14.
 */
public class CreateChildTask extends AsyncTask<ChildDto, Void, Child> {

    private final Bus mBus;
    private final ImageService mService;

    private boolean mWorking = false;

    public CreateChildTask(Bus bus, ImageService service) {
        mBus = bus;
        mService = service;
    }

    @Override
    protected Child doInBackground(ChildDto... dto) {
        mWorking = true;

        ChildDto childDto = dto[0];

        Child child = Child.get();
        child.firstName = childDto.getName();
        child.profilePicture = mService.saveImage(childDto.getBitmap(), childDto.getOrientation(), child);
        child.profilePictureOrientation = childDto.getOrientation();
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
        mWorking = false;

        mBus.post(new ChildCreatedEvent(child));
    }

    public boolean isCurrentlyWorking() {
        return mWorking;
    }
}
