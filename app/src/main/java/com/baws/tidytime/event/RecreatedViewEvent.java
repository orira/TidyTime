package com.baws.tidytime.event;

import com.baws.tidytime.view.PresenterView;

/**
 * Created by wadereweti on 29/07/14.
 */
public class RecreatedViewEvent {
    private final PresenterView mView;

    public RecreatedViewEvent(PresenterView view) {
        mView = view;
    }

    public PresenterView getView() {
        return mView;
    }
}
