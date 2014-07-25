package com.baws.tidytime.presenter;

import android.content.Intent;

/**
 * Created by wadereweti on 25/07/14.
 */
public interface CreateChildPresenter {
    void onImageReturned(int requestCode, int resultCode, Intent data);
}
