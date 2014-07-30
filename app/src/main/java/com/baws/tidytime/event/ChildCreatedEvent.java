package com.baws.tidytime.event;

import com.baws.tidytime.model.Child;

/**
 * Created by wadereweti on 29/07/14.
 */
public class ChildCreatedEvent {
    private final Child mChild;

    public ChildCreatedEvent(Child child) {
        mChild = child;
    }

    public Child getChild() {
        return mChild;
    }
}
