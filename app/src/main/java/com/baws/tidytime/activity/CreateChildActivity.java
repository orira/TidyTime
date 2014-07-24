package com.baws.tidytime.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.baws.tidytime.R;

/**
 * Created by Raukawa on 7/24/2014.
 */
public class CreateChildActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialiseActionBar();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.scale_up_alpha, R.anim.slide_out_bottom);
    }

    private void initialiseActionBar() {
        super.setTitle(getString(R.string.create_child));
        getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
    }
}
