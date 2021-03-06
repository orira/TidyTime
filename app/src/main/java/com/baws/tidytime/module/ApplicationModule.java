package com.baws.tidytime.module;

import android.content.res.Resources;

import com.baws.tidytime.TidyTimeApplication;
import com.baws.tidytime.adapter.AssignedChoreAdapter;
import com.baws.tidytime.module.annotation.ForApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wadereweti on 22/07/14.
 */
@Module(
    injects = {
        TidyTimeApplication.class,
    },
    includes = {
        BusModule.class,
        CacheModule.class,
        ServiceModule.class,
        TaskModule.class
    },
    library = true
)
public class ApplicationModule {
    private TidyTimeApplication mApplication;

    public ApplicationModule(TidyTimeApplication application) {
        mApplication = application;
    }

    @Provides @ForApplication
    public TidyTimeApplication provideApplication() {
        return mApplication;
    }

    @Provides @Singleton
    public Resources provideResources() {
        return mApplication.getResources();
    }
}
