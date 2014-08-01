package com.baws.tidytime.module;

import com.baws.tidytime.asynctask.CreateChoreTask;
import com.baws.tidytime.fragment.CreateChoreFragment;
import com.baws.tidytime.presenter.CreateChorePresenter;
import com.baws.tidytime.presenter.CreateChorePresenterImpl;
import com.baws.tidytime.view.CreateChoreView;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wadereweti on 23/07/14.
 */
@Module(
    injects = CreateChoreFragment.class,
    complete = false
)
public class CreateChoreModule {
    private CreateChoreView mView;

    public CreateChoreModule(CreateChoreView view) {
        mView = view;
    }

    @Provides @Singleton
    CreateChoreView provideView() {
        return mView;
    }

    @Provides @Singleton
    CreateChorePresenter providePresenter(Bus bus, CreateChoreView view, CreateChoreTask task) {
        return new CreateChorePresenterImpl(bus, view, task);
    }
}
