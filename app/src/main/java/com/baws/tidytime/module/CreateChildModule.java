package com.baws.tidytime.module;

import com.baws.tidytime.activity.CreateChildActivity;
import com.baws.tidytime.presenter.CreateChildPresenter;
import com.baws.tidytime.presenter.CreateChildPresenterImpl;
import com.baws.tidytime.view.CreateChildView;

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
    CreateChildPresenter providePresenter(CreateChildView view) {
        return new CreateChildPresenterImpl(view);
    }
}
