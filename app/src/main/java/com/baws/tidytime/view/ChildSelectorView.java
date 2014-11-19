package com.baws.tidytime.view;

import com.baws.tidytime.model.Child;

/**
 * Created by wadereweti on 14/07/14.
 */
public interface ChildSelectorView extends PresenterView {
    void onChildSelected(Child child);
    void onChildDeselected(Child child);
}
