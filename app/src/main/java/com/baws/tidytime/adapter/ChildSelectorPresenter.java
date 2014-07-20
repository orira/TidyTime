package com.baws.tidytime.adapter;

import com.baws.tidytime.model.Child;
import com.baws.tidytime.adapter.ChildSelectorAdapter.ViewHolder;

/**
 * Created by Raukawa on 7/13/2014.
 */
public class ChildSelectorPresenter {
    private static final float SCALE_SELECTED = 1.3f;
    private static final float SCALE_DEFAULT = 1;
    private final Child mChild;
    private boolean mSelected = false;
    private ViewHolder mViewHolder;

    public ChildSelectorPresenter(Child child) {
        mChild = child;
    }

    public void setViewState() {
        if (mSelected) {
            mViewHolder.container.animate().scaleX(SCALE_SELECTED).scaleY(SCALE_SELECTED).alpha(1);
        } else {
            mViewHolder.container.animate().scaleX(SCALE_DEFAULT).scaleY(SCALE_DEFAULT).alpha(.6f);
        }
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }

    public void setViewHolder(ViewHolder viewHolder) {
        mViewHolder = viewHolder;
    }

    public ViewHolder getViewHolder() {
        return mViewHolder;
    }

    public Child getChild() {
        return mChild;
    }
}
