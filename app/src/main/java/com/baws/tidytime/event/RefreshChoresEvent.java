package com.baws.tidytime.event;

/**
 * Created by wadereweti on 5/08/14.
 */
public class RefreshChoresEvent {
    private final boolean mRefresh;

    public RefreshChoresEvent(boolean refresh) {
        mRefresh = refresh;
    }

    public boolean isRefresh() {
        return mRefresh;
    }
}
