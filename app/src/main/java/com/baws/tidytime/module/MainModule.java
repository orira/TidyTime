package com.baws.tidytime.module;

import com.baws.tidytime.activity.MainActivity;
import com.baws.tidytime.presenter.MainPresenter;
import com.baws.tidytime.presenter.MainPresenterImpl;
import com.baws.tidytime.view.MainView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wadereweti on 29/07/14.
 */
@Module(
    injects = MainActivity.class,
    complete = false
)
public class MainModule {

    private MainView mMainView;

    public MainModule(MainView mainView) {
        mMainView = mainView;
    }

    @Provides @Singleton
    MainView provideMainView() {
        return mMainView;
    }

    @Provides @Singleton
    MainPresenter provideMainPresenter(MainView mainView) {
        return new MainPresenterImpl(mainView);
    }
}
