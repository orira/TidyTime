package com.baws.tidytime.module;

import com.baws.tidytime.service.ChildService;
import com.baws.tidytime.service.ChildServiceImpl;
import com.baws.tidytime.service.ChoreService;
import com.baws.tidytime.service.ChoreServiceImpl;
import com.baws.tidytime.service.ImageService;
import com.baws.tidytime.service.ImageServiceImpl;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

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

    @Provides @Singleton
    ImageService provideImageService() {
        return new ImageServiceImpl();
    }

    @Provides @Singleton
    ChoreService provideChoreService(Bus bus) {
        return new ChoreServiceImpl(bus);
    }

    @Provides @Singleton
    ChildService provideChildService(Bus bus, ImageService imageService) {
        return new ChildServiceImpl(bus, imageService);
    }
}
