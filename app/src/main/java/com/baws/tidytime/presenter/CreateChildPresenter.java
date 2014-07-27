package com.baws.tidytime.presenter;

import android.graphics.Bitmap;

/**
 * Created by wadereweti on 25/07/14.
 */
public interface CreateChildPresenter {
    void onImageReturned(Bitmap bitmap, int orientation);
    void createChildRequest(String name);
}
