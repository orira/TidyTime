package com.baws.tidytime.service;

import android.graphics.Bitmap;
import android.util.Log;

import com.baws.tidytime.TidyTimeApplication;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.util.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by wadereweti on 25/07/14.
 */
public class ImageServiceImpl implements ImageService {

    private static final String TAG = "ImageServiceImpl";

    TidyTimeApplication mContext;

    public ImageServiceImpl() {
        mContext = TidyTimeApplication.get();
    }

    @Override
    public String saveImage (Bitmap bitmap, int orientation, Child child) {
        if (bitmap == null) {
            return null;
        }

        File file = new File(mContext.getExternalFilesDir(Constants.BASE_EXTERNAL_DIRECTORY_IMAGES), child.firstName + ".png");

        FileOutputStream fileOutputStream;

        try {
            fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 95, fileOutputStream);
        } catch(FileNotFoundException e) {
            // do something here
            Log.e(TAG, "Couldn't save image");
        }

        return file.getPath();
    }
}

