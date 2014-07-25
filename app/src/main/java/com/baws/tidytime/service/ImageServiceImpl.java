package com.baws.tidytime.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.baws.tidytime.model.Child;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import util.Constants;

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
    public void saveImage (Bitmap thumbnail, int orientation, Child child) {
        File file = new File(mContext.getExternalFilesDir(Constants.BASE_EXTERNAL_DIRECTORY_IMAGES), child.firstName + ".png");

        saveImageToFile(thumbnail, child, orientation, file);
    }

    private void saveImageToFile(Bitmap thumbnail, Child child, int orientation, File file) {
        FileOutputStream fileOutputStream;

        try {
            fileOutputStream = new FileOutputStream(file);
            thumbnail.compress(Bitmap.CompressFormat.PNG, 95, fileOutputStream);
            child.profilePicture = file.getPath();
            //child.imageOrientation = orientation;
            child.save();
        } catch(FileNotFoundException e) {
            // do something here
            Log.e(TAG, "Couldn't save image");
        }
    }
}

