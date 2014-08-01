package com.baws.tidytime.module;

import dagger.Module;

/**
 * Created by wadereweti on 22/07/14.
 */
@Module(
    includes = {
        ApplicationModule.class,
        AssignChorePresenterModule.class,
        BusModule.class,
        ServiceModule.class,
        CreateChildModule.class,
        MainPresenterModule.class,
        TaskModule.class
    },
    complete = false
)
public class BaseModule {

}
