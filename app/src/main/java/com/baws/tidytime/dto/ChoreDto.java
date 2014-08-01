package com.baws.tidytime.dto;

import com.baws.tidytime.model.Child;

/**
 * Created by wadereweti on 1/08/14.
 */
public class ChoreDto {

    private final Child mChild;
    private final String mChoreType;
    private final String mChoreDate;

    public ChoreDto(String choreDate, String choreType, Child child) {
        mChoreDate = choreDate;
        mChoreType = choreType;
        mChild = child;
    }

    public Child getChild() {
        return mChild;
    }

    public String getChoreType() {
        return mChoreType;
    }

    public String getChoreDate() {
        return mChoreDate;
    }
}
