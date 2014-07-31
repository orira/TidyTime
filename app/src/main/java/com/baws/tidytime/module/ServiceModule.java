package com.baws.tidytime.module;

import com.baws.tidytime.asynctask.CreateChildTask;
import com.baws.tidytime.service.ImageService;
import com.baws.tidytime.service.ImageServiceImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wadereweti on 29/07/14.
 */

@Module(
    injects = CreateChildTask.class,
    complete = false
)
public class ServiceModule {

    @Provides
    ImageService provideImageService() {
        return new ImageServiceImpl();
    }
}
