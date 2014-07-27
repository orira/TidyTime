package com.baws.tidytime.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.baws.tidytime.model.Child;

/**
 * Created by Raukawa on 7/26/2014.
 */
public class BitmapUtil {
    public static Bitmap fetchAvatarBitmap(Child child) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        return BitmapFactory.decodeFile(child.profilePicture, options);
    }
}
