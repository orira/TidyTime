package com.baws.tidytime.service;

import android.graphics.Bitmap;

import com.baws.tidytime.model.Child;

/**
 * Created by wadereweti on 25/07/14.
 */
public interface ImageService {
    void saveImage(Bitmap bitmap, int orientation, Child child);
}
