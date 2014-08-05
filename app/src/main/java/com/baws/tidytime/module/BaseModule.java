package com.baws.tidytime.module;

import dagger.Module;

/**
 * Created by wadereweti on 22/07/14.
 */
@Module(
    includes = {
        ApplicationModule.class,
        ActivityModule.class,
        BusModule.class,
        CacheModule.class,
        CreateChoreModule.class,
        CreateChildModule.class,
        MainModule.class,
        TaskModule.class,
        ServiceModule.class,
    }
)
public class BaseModule {

}
