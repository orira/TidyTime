package com.baws.tidytime.presenter;

import android.content.Intent;

/**
 * Created by wadereweti on 25/07/14.
 */
public interface CreateChildPresenter extends Presenter {
    void photoRequested();
    void onImageReturned(int requestCode, int resultCode, Intent data);
    void onNameEntered(CharSequence charSequence);
    void createChildRequest(String name);
}
