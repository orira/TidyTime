package com.baws.tidytime.asynctask;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import com.baws.tidytime.util.BitmapUtil;
import com.baws.tidytime.widget.CircularImageView;

import java.lang.ref.WeakReference;

/**
 * Created by wadereweti on 28/07/14.
 */
public class BitmapTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> mImageViewReference;
    private final WeakReference<LruCache<String, Bitmap>> mBitmapCacheReference;

    public BitmapTask(CircularImageView imageView, LruCache<String, Bitmap> bitmapCache) {
        super();
        mImageViewReference = new WeakReference<ImageView>(imageView);
        mBitmapCacheReference = new WeakReference<LruCache<String, Bitmap>>(bitmapCache);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String filePath = params[0];
        String bitmapKey = params[1];

        Bitmap bitmap = BitmapUtil.fetchAvatarBitmap(filePath);
        mBitmapCacheReference.get().put(bitmapKey, bitmap);

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if (mImageViewReference != null && bitmap != null) {
            final ImageView imageView = mImageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
