package com.baws.tidytime.module;

import com.baws.tidytime.fragment.AssignChoreFragment;
import com.baws.tidytime.presenter.AssignChorePresenterImpl;
import com.baws.tidytime.presenter.AssignFragmentPresenter;
import com.baws.tidytime.view.AssignView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wadereweti on 23/07/14.
 */
@Module(
    injects = AssignChoreFragment.class,
    complete = false
)

public class AssignChorePresenterModule {
    private AssignView mView;

    public AssignChorePresenterModule(AssignView view) {
        mView = view;
    }

    @Provides
    AssignView provideView() {
        return mView;
    }

    @Provides
    AssignFragmentPresenter providePresenter(AssignView view) {
        return new AssignChorePresenterImpl(view);
    }
}
