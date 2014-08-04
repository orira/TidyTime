package com.baws.tidytime.module;

import com.baws.tidytime.service.ChoreService;
import com.baws.tidytime.service.ChoreServiceImpl;
import com.baws.tidytime.service.ImageService;
import com.baws.tidytime.service.ImageServiceImpl;
import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wadereweti on 29/07/14.
 */

@Module(
    library = true,
    complete = false
)
public class ServiceModule {

    @Provides
    ImageService provideImageService() {
        return new ImageServiceImpl();
    }

    @Provides
    ChoreService provideChoreService(Bus bus) {
        return new ChoreServiceImpl(bus);
    }
}
