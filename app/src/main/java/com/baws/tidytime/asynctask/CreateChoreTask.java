package com.baws.tidytime.asynctask;

import com.baws.tidytime.dto.ChoreDto;
import com.baws.tidytime.event.ChoreCreatedEvent;
import com.baws.tidytime.model.Chore;
import com.baws.tidytime.util.DateUtil;
import com.squareup.otto.Bus;

import java.util.List;

/**
 * Created by wadereweti on 21/07/14.
 */
public class CreateChoreTask extends AbstractTask<Void, Void, Boolean> {

    private List<ChoreDto> mDtos;

    public CreateChoreTask(Bus bus, List<ChoreDto> dtos) {
        super(bus);
        mDtos = dtos;
    }

    @Override
    protected Boolean doInBackground(Void... aVoid) {
        mutateWorkingState();

        for (ChoreDto dto : mDtos) {
            Chore chore = new Chore();
            chore.description = dto.getChoreType();
            chore.assignedDate = DateUtil.getCurrentDate();
            chore.dueDate = DateUtil.getFormattedDate(dto.getChoreDate());
            chore.child = dto.getChild();
            chore.complete = false;

            chore.save();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean created) {
        mutateWorkingState();
        mBus.post(new ChoreCreatedEvent(created));
    }
}
