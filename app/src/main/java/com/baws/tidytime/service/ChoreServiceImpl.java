package com.baws.tidytime.service;

import com.baws.tidytime.asynctask.CreateChoreTask;
import com.baws.tidytime.dto.ChoreDto;
import com.baws.tidytime.event.ChoreCreatedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Created by wadereweti on 21/07/14.
 */
public class ChoreServiceImpl extends AbstractService implements ChoreService {

    private CreateChoreTask mTask;

    public ChoreServiceImpl(Bus bus) {
        super(bus);
    }

    @Override
    public void createChore(ChoreDto dto) {
        mutateWorkingState();

        mTask = new CreateChoreTask(mBus);
        mTask.execute(dto);
    }

    @Subscribe
    public void onChoreCreated(ChoreCreatedEvent event) {
        if (event.getChore() != null) {
            mutateWorkingState();
        }
    }
}
