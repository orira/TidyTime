package com.baws.tidytime.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private final ChildSelectorView mChildSelectorView;

    public ChildSelectorAdapter(List<Child> children, Context context, ChildSelectorView childSelectorView) {
        mChildren = children;
        mLayoutInflater = LayoutInflater.from(context);
        mChildSelectorView = childSelectorView;
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
        }

        Child child = mChildren.get(position);
        Bitmap bitmap = BitmapFactory.decodeFile(child.profilePicture);

        viewHolder.imageView.setImageBitmap(bitmap);

        return view;
    }

    public static class ViewHolder {
        @InjectView(R.id.iv_profile_picture) public CircularImageView imageView;

        private ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
