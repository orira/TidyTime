package com.baws.tidytime.asynctask;

import android.os.AsyncTask;

import com.baws.tidytime.BusUtil;
import com.baws.tidytime.event.ChoreCreatedEvent;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.model.Chore;

import util.DateUtil;

/**
 * Created by wadereweti on 21/07/14.
 */
public class CreateChoreTask extends AsyncTask<String, Void, Boolean> {

    private Child child;

    public CreateChoreTask(Child mChildSelected) {
        child = mChildSelected;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        Chore chore = new Chore();
        chore.description = strings[0];
        chore.assignedDate = DateUtil.getCurrentDate();
        chore.dueDate = DateUtil.getFormattedDate(strings[1]);
        chore.child = child;
        chore.complete = false;

        chore.save();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean created) {
        BusUtil.get().post(new ChoreCreatedEvent(created));
    }
}
