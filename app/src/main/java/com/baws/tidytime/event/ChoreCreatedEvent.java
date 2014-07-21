package com.baws.tidytime.event;

import com.activeandroid.ActiveAndroid;

/**
 * Created by wadereweti on 21/07/14.
 */
public class ChoreCreatedEvent {

    boolean mCreated;

    public ChoreCreatedEvent(boolean created) {
        mCreated = created;
    }

    public boolean isCreated() {
        return mCreated;
    }
}
