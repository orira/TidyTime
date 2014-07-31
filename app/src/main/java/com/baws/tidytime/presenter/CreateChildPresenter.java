package com.baws.tidytime.presenter;

import android.content.Intent;
import android.graphics.Bitmap;

/**
 * Created by wadereweti on 25/07/14.
 */
public interface CreateChildPresenter {
    void photoRequested();
    void onImageReturned(int requestCode, int resultCode, Intent data);
    void createChildRequest(String name);
}
