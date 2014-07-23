package com.baws.tidytime.event;

import com.baws.tidytime.model.Chore;

/**
 * Created by wadereweti on 21/07/14.
 */
public class ChoreCreatedEvent {

    private boolean mCreated;
    private Chore mChore;

    public ChoreCreatedEvent(boolean created, Chore chore) {
        mCreated = created;
        mChore = chore;
    }

    public boolean isCreated() {
        return mCreated;
    }

    public Chore getChore() {
        return mChore;
    }
}
