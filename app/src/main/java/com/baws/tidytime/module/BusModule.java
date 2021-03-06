package com.baws.tidytime.module;

import com.baws.tidytime.asynctask.BitmapTask;
import com.baws.tidytime.asynctask.CreateChoreTask;
import com.baws.tidytime.fragment.AssignedChoreFragment;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Raukawa on 7/2/2014.
 */

@Module(
    injects = {
        AssignedChoreFragment.class,
        BitmapTask.class,
        CreateChoreTask.class
    }
)
public class BusModule {

    @Provides @Singleton
    Bus provideBus() {
        return new Bus();
    }
}
