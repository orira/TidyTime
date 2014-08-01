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
        TaskModule.class,
        ServiceModule.class,
        MainModule.class,
        AssignChorePresenterModule.class,
        CreateChildModule.class,
    }
)
public class BaseModule {

}
