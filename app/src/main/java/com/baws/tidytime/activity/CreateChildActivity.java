package com.baws.tidytime.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.baws.tidytime.R;
import com.baws.tidytime.presenter.CreateChildPresenter;
import com.baws.tidytime.presenter.CreateChildPresenterImpl;
import com.baws.tidytime.view.CreateChildView;
import com.baws.tidytime.widget.CircularImageView;
import com.iangclifton.android.floatlabel.FloatLabel;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Raukawa on 7/24/2014.
 */
public class CreateChildActivity extends AbstractActivity implements CreateChildView {

    private static final String TAG = "CreateChildActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_FILE = 0;

    private CreateChildPresenter mPresenter;

    @InjectView(R.id.iv_profile_picture) CircularImageView mProfilePicture;
    @InjectView(R.id.fl_enter_name) FloatLabel mNameEditText;
    @InjectView(R.id.imageview) ImageView im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_child);
        ButterKnife.inject(this);

        mPresenter = new CreateChildPresenterImpl(this);
        initialiseActionBar();
        initialiseInput();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_child, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                reverseActivityAnimation();
                return true;
            case R.id.action_add_child:
                createNewPerson();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        reverseActivityAnimation();
    }

    private void reverseActivityAnimation() {
        finish();
        overridePendingTransition(R.anim.scale_up_alpha, R.anim.slide_out_bottom);
    }

    private void initialiseActionBar() {
        super.setTitle(getString(R.string.create_child));
        getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
    }

    private void initialiseInput() {
        mNameEditText.setLabel(R.string.enter_name);
        mProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }

    private void createNewPerson() {
        if (TextUtils.isEmpty(mNameEditText.getEditText().getText().toString())) {
            showErrorToast();
        } else {
            mPresenter.createChildRequest(mNameEditText.getEditText().getText().toString());
        }
    }

    private void showErrorToast() {
        Toast.makeText(this, getString(R.string.error_no_details_entered), Toast.LENGTH_LONG).show();
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(CreateChildActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_IMAGE_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        int orientation = 0;
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                bitmap = (Bitmap) extras.get("data");
            } else if (requestCode == REQUEST_IMAGE_FILE) {
                try {
                    Uri imageUri = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    orientation = getPhotoOrientation(imageUri);
                } catch (Exception exception) {
                    Log.e(TAG, "File not found, or io exception");
                }
            }

            if (bitmap != null) {
                im.setImageBitmap(bitmap);
                mProfilePicture.setImageBitmap(bitmap);
                mPresenter.onImageReturned(bitmap, orientation);
            }
        }
    }

    public int getPhotoOrientation(Uri uri) {
        int orientation = 0;
        Cursor cursor = getApplicationContext().getContentResolver().query(uri, new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            orientation = cursor.getInt(0);
        }

        return orientation;
    }

    @Override
    public void onChildCreated() {

    }
}
