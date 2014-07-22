package com.baws.tidytime.asynctask;

import android.os.AsyncTask;

import com.baws.tidytime.BusUtil;
import com.baws.tidytime.event.ChoreCreatedEvent;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.model.Chore;
import com.baws.tidytime.module.BusModule;
import com.squareup.otto.Bus;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import util.DateUtil;

/**
 * Created by wadereweti on 21/07/14.
 */
public class CreateChoreTask extends AbstractTask<String, Void, Boolean> {

    @Inject
    Bus mBus;

    private Child child;

    public CreateChoreTask(Child mChildSelected) {
        super();
        child = mChildSelected;
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new BusModule());
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
        mBus.post(new ChoreCreatedEvent(created));
    }
}
