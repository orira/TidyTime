package com.baws.tidytime;

import com.squareup.otto.Bus;

/**
 * Created by wadereweti on 21/07/14.
 */
public class BusUtil {

    private static Bus mBus;

    public static Bus get() {
        if (mBus == null) {
            mBus = new Bus();
        }

        return mBus;
    }
}
