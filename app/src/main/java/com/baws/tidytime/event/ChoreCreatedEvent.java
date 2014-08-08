package com.baws.tidytime.event;

/**
 * Created by wadereweti on 21/07/14.
 */
public class ChoreCreatedEvent {

    private boolean mCreated;

    public ChoreCreatedEvent(boolean created) {
        mCreated = created;
    }

    public boolean isCreated() {
        return mCreated;
    }
}
