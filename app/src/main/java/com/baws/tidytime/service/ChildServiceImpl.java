package com.baws.tidytime.service;

import com.baws.tidytime.asynctask.CreateChildTask;
import com.baws.tidytime.dto.ChildDto;
import com.baws.tidytime.event.ChildCreatedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Created by wadereweti on 5/08/14.
 */
public class ChildServiceImpl extends AbstractService implements ChildService {

    private final ImageService mImageService;

    public ChildServiceImpl(Bus bus, ImageService imageService) {
        super(bus);
        mImageService = imageService;
    }

    @Override
    public void createChild(ChildDto dto) {
        mutateWorkingState();
        new CreateChildTask(mBus, mImageService).execute(dto);
    }

    @Subscribe
    public void onChildCreated(ChildCreatedEvent event) {
        if (event.getChild() != null) {
            mutateWorkingState();
        }
    }
}
