package com.baws.tidytime.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import com.baws.tidytime.activity.CreateChildActivity;
import com.baws.tidytime.activity.MainActivity;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.service.ImageService;
import com.baws.tidytime.service.ImageServiceImpl;
import com.baws.tidytime.view.CreateChildView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by wadereweti on 25/07/14.
 */
public class CreateChildPresenterImpl implements CreateChildPresenter {

    static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 100;

    private CreateChildView mView;
    private ImageService mImageService;

    public CreateChildPresenterImpl(CreateChildView view) {
        mView = view;
        mImageService = new ImageServiceImpl((CreateChildActivity) view);
    }

    /*@Override
    public void onImageReturned(int requestCode, int resultCode, Intent data, Child child) {
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        mImageService.saveImage(bitmap, 0, child);



        *//*else if (requestCode == SELECT_FILE) {
            Uri selectedImageUri = data.getData();

            String tempPath = getPath(selectedImageUri, MainActivity.this);
            Bitmap bm;
            BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
            bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
            ivImage.setImageBitmap(bm);
        }*//*
    }*/

    @Override
    public void onImageReturned(int requestCode, int resultCode, Intent data) {

    }
}
