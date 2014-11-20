package com.baws.tidytime.activity;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;

import com.baws.tidytime.R;
import com.baws.tidytime.TidyTimeApplication;
import com.baws.tidytime.typeface.RobotoTypeface;
import com.baws.tidytime.widget.TypefaceSpan;
import com.squareup.otto.Bus;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.ObjectGraph;

/**
 * Created by wadereweti on 6/07/14.
 */
public class AbstractActivity extends ActionBarActivity {

    private ObjectGraph mFragmentObjectGraph;
    private Toolbar mToolbar;

    @Inject
    Bus mBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentObjectGraph = TidyTimeApplication.get().createScopedGraph(getModules().toArray());
        mFragmentObjectGraph.inject(this);

        mBus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
        mFragmentObjectGraph = null;
    }

    protected List<Object> getModules() {
        return Arrays.<Object>asList();
    }

    /*protected void initialisesActionBar() {
        setTitle(null);

        // Set some padding between icon and title
        ImageView icon = (ImageView) findViewById(android.R.id.home);

        if (icon != null) {
            FrameLayout.LayoutParams iconLp = (FrameLayout.LayoutParams) icon.getLayoutParams();
            iconLp.rightMargin = (int) getResources().getDimension(R.dimen.margin_default_x1_5);
            icon.setLayoutParams(iconLp);
        }
    }*/

    protected void initialisesActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        mToolbar.setLogo(getResources().getDrawable(R.drawable.ic_launcher));
        mToolbar.setTitle(getSpannableTitleString(getResources().getString(R.string.app_name)));
    }

    protected void setLogo(Drawable drawable) {
        if (mToolbar == null) {
            initialisesActionBar();
        }

        Drawable logo = drawable == null ? new ColorDrawable(getResources().getColor(android.R.color.transparent)) : drawable;
        mToolbar.setLogo(logo);

        if (drawable == null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void setTitle(String title) {
        if (mToolbar == null) {
            initialisesActionBar();
        }

        if (title != null) {
            mToolbar.setTitle(getSpannableTitleString(title));
        } else {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private SpannableString getSpannableTitleString(String title) {
        SpannableString spannableString = new SpannableString(title);
        spannableString.setSpan(new TypefaceSpan(this, RobotoTypeface.LIGHT), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    protected Toolbar getActionBarToolBar() {
        return mToolbar;
    }
}
