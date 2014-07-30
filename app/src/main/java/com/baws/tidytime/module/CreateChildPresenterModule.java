package com.baws.tidytime.module;

import com.baws.tidytime.activity.CreateChildActivity;
import com.baws.tidytime.presenter.CreateChildPresenter;
import com.baws.tidytime.presenter.CreateChildPresenterImpl;
import com.baws.tidytime.view.CreateChildView;
import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wadereweti on 29/07/14.
 */
@Module(
    injects = CreateChildActivity.class
)
public class CreateChildPresenterModule {

    private CreateChildView mView;

    public CreateChildPresenterModule(CreateChildView view) {
        mView = view;
    }

    @Provides
    CreateChildView provideView() {
        return mView;
    }

    @Provides
    CreateChildPresenter providePresenter(CreateChildView view) {
        return new CreateChildPresenterImpl(view);
    }
}
