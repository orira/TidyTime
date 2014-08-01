package com.baws.tidytime.module;

import com.baws.tidytime.asynctask.CreateChildTask;
import com.baws.tidytime.service.ImageService;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wadereweti on 1/08/14.
 */

@Module(
    library = true,
    complete = false
)
public class TaskModule {

    @Provides @Singleton
    public CreateChildTask provideCreateChildTask(Bus bus, ImageService service) {
        return new CreateChildTask(bus, service);
    }
}
