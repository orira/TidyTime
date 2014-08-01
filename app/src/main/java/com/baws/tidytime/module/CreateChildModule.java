package com.baws.tidytime.module;

import android.content.Context;

import com.baws.tidytime.TidyTimeApplication;
import com.baws.tidytime.activity.CreateChildActivity;
import com.baws.tidytime.asynctask.CreateChildTask;
import com.baws.tidytime.module.annotation.ForApplication;
import com.baws.tidytime.presenter.CreateChildPresenter;
import com.baws.tidytime.presenter.CreateChildPresenterImpl;
import com.baws.tidytime.view.CreateChildView;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wadereweti on 29/07/14.
 */
@Module(
    injects = CreateChildActivity.class,
    complete = false
)
public class CreateChildModule {

    private CreateChildView mView;

    public CreateChildModule(CreateChildView view) {
        mView = view;
    }

    @Provides @Singleton
    CreateChildView provideView() {
        return mView;
    }

    @Provides @Singleton
    CreateChildPresenter providePresenter(Bus bus, CreateChildView view, @ForApplication TidyTimeApplication context, CreateChildTask task) {
        return new CreateChildPresenterImpl(bus, view, context, task);
    }
}
