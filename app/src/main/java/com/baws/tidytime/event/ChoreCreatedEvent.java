package com.baws.tidytime.event;

import com.baws.tidytime.model.Chore;

/**
 * Created by wadereweti on 21/07/14.
 */
public class ChoreCreatedEvent {

    private Chore mChore;

    public ChoreCreatedEvent(Chore chore) {
        mChore = chore;
    }

    public Chore getChore() {
        return mChore;
    }
}
