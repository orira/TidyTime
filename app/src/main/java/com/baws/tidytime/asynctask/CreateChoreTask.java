package com.baws.tidytime.asynctask;

import com.baws.tidytime.event.ChoreCreatedEvent;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.model.Chore;
import com.baws.tidytime.util.DateUtil;

/**
 * Created by wadereweti on 21/07/14.
 */
public class CreateChoreTask extends AbstractTask<String, Void, Chore> {

    private Child child;

    public CreateChoreTask(Child mChildSelected) {
        super();
        child = mChildSelected;
    }

    @Override
    protected Chore doInBackground(String... strings) {
        Chore chore = new Chore();
        chore.description = strings[0];
        chore.assignedDate = DateUtil.getCurrentDate();
        chore.dueDate = DateUtil.getFormattedDate(strings[1]);
        chore.child = child;
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
        mBus.post(new ChoreCreatedEvent(chore));
    }
}
