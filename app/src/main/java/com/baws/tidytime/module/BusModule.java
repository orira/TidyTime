package com.baws.tidytime.module;

import com.baws.tidytime.asynctask.CreateChoreTask;
import com.baws.tidytime.presenter.AssignFragmentPresenterImpl;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Raukawa on 7/2/2014.
 */

@Module(
    injects = {
        AssignFragmentPresenterImpl.class,
        CreateChoreTask.class
    }
)
public class BusModule {

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }
}