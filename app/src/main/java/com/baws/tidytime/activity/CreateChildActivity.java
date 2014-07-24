package com.baws.tidytime.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.baws.tidytime.R;
import com.baws.tidytime.widget.CircularImageView;
import com.iangclifton.android.floatlabel.FloatLabel;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Raukawa on 7/24/2014.
 */
public class CreateChildActivity extends AbstractActivity {

    @InjectView(R.id.iv_profile_picture) CircularImageView mProfilePicture;
    @InjectView(R.id.fl_enter_name) FloatLabel mNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_child);
        ButterKnife.inject(this);

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

    private void initialiseActionBar() {
        super.setTitle(getString(R.string.create_child));
        getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
    }

    private void initialiseInput() {
        mNameEditText.setLabel(R.string.enter_name);
    }

    private void createNewPerson() {

    }

    private void reverseActivityAnimation() {
        finish();
        overridePendingTransition(R.anim.scale_up_alpha, R.anim.slide_out_bottom);
    }
}
