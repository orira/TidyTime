package com.baws.tidytime.asynctask;

import com.baws.tidytime.dto.ChoreDto;
import com.baws.tidytime.event.ChoreCreatedEvent;
import com.baws.tidytime.model.Chore;
import com.baws.tidytime.util.DateUtil;
import com.squareup.otto.Bus;

/**
 * Created by wadereweti on 21/07/14.
 */
public class CreateChoreTask extends AbstractTask<ChoreDto, Void, Chore> {

    public CreateChoreTask(Bus bus) {
        super(bus);
    }

    @Override
    protected Chore doInBackground(ChoreDto... dtos) {
        mutateWorkingState();

        ChoreDto dto = dtos[0];

        Chore chore = new Chore();
        chore.description = dto.getChoreType();
        chore.assignedDate = DateUtil.getCurrentDate();
        chore.dueDate = DateUtil.getFormattedDate(dto.getChoreDate());
        chore.child = dto.getChild();
        chore.complete = false;

        chore.save();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return chore;
    }

    @Override
    protected void onPostExecute(Chore chore) {
        mutateWorkingState();
        mBus.post(new ChoreCreatedEvent(chore));
    }
}
