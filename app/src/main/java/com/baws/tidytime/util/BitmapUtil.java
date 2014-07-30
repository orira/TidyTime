package com.baws.tidytime.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.baws.tidytime.model.Child;

/**
 * Created by Raukawa on 7/26/2014.
 */
public class BitmapUtil {

    public static Bitmap fetchAvatarBitmap(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 6;
        return BitmapFactory.decodeFile(filePath, options);
    }
}
