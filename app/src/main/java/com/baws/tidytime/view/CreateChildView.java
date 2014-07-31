package com.baws.tidytime.view;

import android.app.AlertDialog;
import android.graphics.Bitmap;

/**
 * Created by wadereweti on 25/07/14.
 */
public interface CreateChildView extends PresenterView {
    void initialiseActionBar();
    void initialiseInput();
    void onInvalidInput();
    void showDialog(AlertDialog.Builder builder);
    void setProfileImage(Bitmap bitmap);
    void displayCreateState();
    void onChildCreated();
}
