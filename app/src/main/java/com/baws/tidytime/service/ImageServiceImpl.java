package com.baws.tidytime.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.baws.tidytime.model.Child;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.baws.tidytime.util.Constants;

/**
 * Created by wadereweti on 25/07/14.
 */
public class ImageServiceImpl implements ImageService{

    private static final String TAG = "ImageServiceImpl";

    private Context mContext;

    public ImageServiceImpl(Context context) {
        mContext = context;
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

