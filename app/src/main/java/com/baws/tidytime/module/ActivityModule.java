package com.baws.tidytime.module;

import android.content.Context;

import com.baws.tidytime.module.annotation.ForActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wadereweti on 1/08/14.
 */

@Module(
    addsTo = ApplicationModule.class,
    library = true
)
public class ActivityModule {

    Context mContext;

    public ActivityModule(Context context) {
        mContext = context;
    }

    @Provides @Singleton @ForActivity
    Context provideActivityContext() {
        return mContext;
    }
}
