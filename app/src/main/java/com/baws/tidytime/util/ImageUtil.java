package com.baws.tidytime.util;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.baws.tidytime.TidyTimeApplication;

/**
 * Created by wadereweti on 31/07/14.
 */
public class ImageUtil {
    public static int getPhotoOrientation(Uri uri) {
        int orientation = 0;
        Cursor cursor = TidyTimeApplication.get().getContentResolver().query(uri, new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            orientation = cursor.getInt(0);
        }

        return orientation;
    }
}
