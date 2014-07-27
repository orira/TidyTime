package com.baws.tidytime.module;

import android.content.res.Resources;

import com.baws.tidytime.TidyTimeApplication;
import com.baws.tidytime.asynctask.CreateChoreTask;
import com.baws.tidytime.fragment.AssignFragment;
import com.baws.tidytime.fragment.AssignedFragment;
import com.baws.tidytime.module.annotation.ForApplication;
import com.baws.tidytime.presenter.AssignFragmentPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wadereweti on 22/07/14.
 */
@Module(
    injects = {
        TidyTimeApplication.class,
        AssignedFragment.class,
        AssignFragmentPresenterImpl.class,
        CreateChoreTask.class
    },
    includes = {
            BusModule.class
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
