package com.baws.tidytime.module;

import android.content.Context;

import com.baws.tidytime.fragment.AssignFragment;
import com.baws.tidytime.presenter.AssignFragmentPresenter;
import com.baws.tidytime.presenter.AssignFragmentPresenterImpl;
import com.baws.tidytime.view.AssignView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wadereweti on 23/07/14.
 */
@Module(
    injects = AssignFragment.class
)
public class AssignViewModule {
    private AssignView mView;
    private Context mContext;

    public AssignViewModule(AssignView view, Context context) {
        mView = view;
        mContext = context;
    }

    @Provides
    AssignView provideView() {
        return mView;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }

    @Provides
    AssignFragmentPresenter providePresenter(AssignView view, Context context) {
        return new AssignFragmentPresenterImpl(view, context);
    }
}
