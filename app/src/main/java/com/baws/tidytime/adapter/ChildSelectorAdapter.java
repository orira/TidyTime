package com.baws.tidytime.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.baws.tidytime.R;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.view.ChildSelectorView;
import com.baws.tidytime.widget.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by wadereweti on 9/07/14.
 */
public class ChildSelectorAdapter extends BaseAdapter {

    private static final String TAG = "ChildSelectorAdapter";

    private final List<Child> mChildren;
    private final LayoutInflater mLayoutInflater;
    private final Resources mResources;
    private List<ChildSelectorPresenter> mPresenters = new ArrayList<ChildSelectorPresenter>();
    private final ChildSelectorView mChildSelectorView;

    public ChildSelectorAdapter(List<Child> children, Context context, ChildSelectorView childSelectorView) {
        mChildren = children;
        mLayoutInflater = LayoutInflater.from(context);
        mResources = context.getResources();
        mChildSelectorView = childSelectorView;

        initialisePresenters();
    }

    private void initialisePresenters() {
        for (Child child : mChildren) {
            mPresenters.add(new ChildSelectorPresenter(child));
        }
    }

    @Override
    public int getCount() {
        return mChildren.size();
    }

    @Override
    public Object getItem(int position) {
        return mChildren.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mChildren.get(position).getId();
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;

        if (view != null) {
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = mLayoutInflater.inflate(R.layout.gridview_child_selector, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);

            // On initialisation sometimes position 0 is called more than once leading to stale viewholder
            // references, so we check for existence first.
            if (mPresenters.get(position).getViewHolder() == null) {
                mPresenters.get(position).setViewHolder(viewHolder);
            }
        }

        Child child = mChildren.get(position);
        Bitmap bitmap = BitmapFactory.decodeFile(child.profilePicture);

        viewHolder.imageView.setImageBitmap(bitmap);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View parentView = (View) view.getParent();
                ViewHolder parentViewHolder = (ViewHolder) parentView.getTag();

                for (int i = 0; i < mPresenters.size(); i++) {
                    ChildSelectorPresenter presenter = mPresenters.get(i);
                    if (presenter.getViewHolder().equals(parentViewHolder)) {
                        boolean selectionState = presenter.isViewSelected() ? false : true;
                        presenter.setSelected(selectionState);
                        Child selectedChild = selectionState ? presenter.getChild() : null;
                        mChildSelectorView.onChildSelected(selectedChild);
                    } else {
                        presenter.setSelected(false);
                    }

                    presenter.setViewState();
                }
            }
        });

        return view;
    }

    public static class ViewHolder {
        @InjectView(R.id.container_profile_picture) RelativeLayout container;
        @InjectView(R.id.iv_profile_picture) public CircularImageView imageView;

        private ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
