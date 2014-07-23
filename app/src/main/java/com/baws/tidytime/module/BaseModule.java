package com.baws.tidytime.module;

import dagger.Module;

/**
 * Created by wadereweti on 22/07/14.
 */
@Module(
    includes = {
        ApplicationModule.class,
        AssignViewModule.class,
        BusModule.class
    }
)
public class BaseModule {

}
