package com.baws.tidytime.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.baws.tidytime.R;
import com.baws.tidytime.TidyTimeApplication;
import com.baws.tidytime.module.ActivityModule;
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
public class AbstractActivity extends FragmentActivity {

    private ObjectGraph mFragmentObjectGraph;

    @Inject
    Bus mBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentObjectGraph = TidyTimeApplication.get().createScopedGraph(getModules().toArray());
        mFragmentObjectGraph.inject(this);

        mBus.register(this);

        initialisesActionBar();
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

    private void initialisesActionBar() {
        setTitle(null);

        // Set some padding between icon and title
        ImageView icon = (ImageView) findViewById(android.R.id.home);
        FrameLayout.LayoutParams iconLp = (FrameLayout.LayoutParams) icon.getLayoutParams();
        iconLp.rightMargin = (int) getResources().getDimension(R.dimen.margin_default_x1_5);
        icon.setLayoutParams(iconLp);
    }

    protected void setTitle(String title) {
        String actionBarTitle = title != null ? title : getResources().getString(R.string.app_name);
        SpannableString s = new SpannableString(actionBarTitle);
        s.setSpan(new TypefaceSpan(this, RobotoTypeface.LIGHT), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the TypefaceSpan instance
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(s);
    }
}
