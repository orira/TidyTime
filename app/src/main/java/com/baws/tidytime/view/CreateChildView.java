package com.baws.tidytime.view;

/**
 * Created by wadereweti on 25/07/14.
 */
public interface CreateChildView extends PresenterView {
    void initialiseActionBar();
    void initialiseInput();
    void onChildCreated();
}
