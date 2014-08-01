package com.baws.tidytime.dto;

import android.graphics.Bitmap;

/**
 * Created by wadereweti on 1/08/14.
 */
public class ChildDto {

    private final Bitmap mBitmap;
    private final int mOrientation;
    private final String mName;

    public ChildDto(Bitmap bitmap, int orientation, String name) {
        mBitmap = bitmap;
        mOrientation = orientation;
        mName = name;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public int getOrientation() {
        return mOrientation;
    }

    public String getName() {
        return mName;
    }
}
