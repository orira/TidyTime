package com.baws.tidytime.asynctask;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.baws.tidytime.util.BitmapUtil;

import java.lang.ref.WeakReference;

/**
 * Created by wadereweti on 28/07/14.
 */
public class BitmapTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> mImageViewReference;
    private String mFilePath;

    public BitmapTask(ImageView imageView) {
        super();
        mImageViewReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        mFilePath = params[0];

        return BitmapUtil.fetchAvatarBitmap(mFilePath);
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
