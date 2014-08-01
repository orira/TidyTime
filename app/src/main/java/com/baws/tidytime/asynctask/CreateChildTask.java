package com.baws.tidytime.asynctask;

import com.baws.tidytime.dto.ChildDto;
import com.baws.tidytime.event.ChildCreatedEvent;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.service.ImageService;
import com.squareup.otto.Bus;

/**
 * Created by wadereweti on 29/07/14.
 */
public class CreateChildTask extends AbstractTask<ChildDto, Void, Child> {

    private final ImageService mService;

    public CreateChildTask(Bus bus, ImageService service) {
        super(bus);
        mService = service;
    }

    @Override
    protected Child doInBackground(ChildDto... dto) {
        mutateWorkingState();

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
        mutateWorkingState();

        mBus.post(new ChildCreatedEvent(child));
    }
}
