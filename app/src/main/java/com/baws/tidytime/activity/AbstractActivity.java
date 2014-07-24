package com.baws.tidytime.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.baws.tidytime.R;
import com.baws.tidytime.typeface.RobotoTypeface;
import com.baws.tidytime.widget.TypefaceSpan;

/**
 * Created by wadereweti on 6/07/14.
 */
public class AbstractActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialisesActionBar();
    }

    private void initialisesActionBar() {
        SpannableString s = new SpannableString(getResources().getString(R.string.app_name));
        s.setSpan(new TypefaceSpan(this, RobotoTypeface.LIGHT), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the TypefaceSpan instance
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(s);

        // Set some padding between icon and title
        ImageView icon = (ImageView) findViewById(android.R.id.home);
        FrameLayout.LayoutParams iconLp = (FrameLayout.LayoutParams) icon.getLayoutParams();
        iconLp.rightMargin = (int) getResources().getDimension(R.dimen.margin_default_x1_5);
        icon.setLayoutParams(iconLp);
    }
}
