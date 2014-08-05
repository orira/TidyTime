package com.baws.tidytime.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.baws.tidytime.R;
import com.baws.tidytime.asynctask.BitmapTask;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.model.Chore;
import com.baws.tidytime.view.AvatarView;
import com.baws.tidytime.widget.CircularImageView;
import com.baws.tidytime.widget.RobotoTextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import dagger.ObjectGraph;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by wadereweti on 7/07/14.
 */
public class AssignedChoreAdapter extends BaseAdapter implements StickyListHeadersAdapter, AvatarView {

    private static final String TAG = "AssignedChoreAdapter";

    private final LayoutInflater mInflater;
    private List<Chore> mChores = new ArrayList<Chore>();

    @Inject LruCache<String, Bitmap> mBitmapCache;

    public AssignedChoreAdapter(Context context, List<Child> children, ObjectGraph objectGraph) {
        mInflater = LayoutInflater.from(context);
        setData(children);
        objectGraph.inject(this);
    }

    @Override
    public Bitmap getBitmapFromCache(String key) {
        return mBitmapCache.get(key);
    }

    public void setData(List<Child> children) {
        mChores.clear();

        for (Child child : children) {
            mChores.addAll(child.chores());
        }
    }

    @Override
    public View getHeaderView(int position, View view, ViewGroup viewGroup) {
        HeaderViewHolder headerViewHolder;

        /** When checking the holder correctly we get incorrect behaviour with the bitmap **/
        /*if (view == null) {
            view = mInflater.inflate(R.layout.list_view_assigned_header, viewGroup, false);
            headerViewHolder = new HeaderViewHolder(view);
            view.setTag(headerViewHolder);
        } else {
            headerViewHolder = (HeaderViewHolder) view.getTag();
        }*/

        view = mInflater.inflate(R.layout.list_view_assigned_header, viewGroup, false);
        headerViewHolder = new HeaderViewHolder(view);
        view.setTag(headerViewHolder);

        Bitmap bitmap = null;

        Child child = mChores.get(position).child;

        if (getBitmapFromCache(child.getId().toString()) == null) {
            if (child.profilePicture != null) {
                BitmapTask bitmapTask = new BitmapTask(headerViewHolder.profilePicture, mBitmapCache);
                bitmapTask.execute(child.profilePicture, child.getId().toString());
            } else {
                headerViewHolder.profilePicture.setImageResource(R.drawable.image_placeholder);
            }
        } else {
            headerViewHolder.profilePicture.setImageBitmap(getBitmapFromCache(child.getId().toString()));
        }


        headerViewHolder.profileName.setText(child.firstName);
        int color = Color.parseColor(child.profileColour);
        headerViewHolder.profilePicture.setBorderColor(color);
        headerViewHolder.profileName.setTextColor(color);

        return view;
    }

    @Override
    public long getHeaderId(int position) {
        return mChores.get(position).child.getId();
    }

    @Override
    public int getCount() {
        return mChores.size();
    }

    @Override
    public Object getItem(int position) {
        return mChores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mChores.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view != null) {
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = mInflater.inflate(R.layout.list_view_assigned_body, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        viewHolder.assignedChore.setText(mChores.get(position).description);

        return view;
    }

     static class HeaderViewHolder {
        @InjectView(R.id.rl_root_container_main_header)
        RelativeLayout rootContainer;

         @InjectView(R.id.iv_profile_picture)
        CircularImageView profilePicture;

        @InjectView(R.id.tv_profile_name)
        RobotoTextView profileName;

        private HeaderViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    static class ViewHolder {
        @InjectView(R.id.tv_assigned_chore)
        RobotoTextView assignedChore;

        @InjectView(R.id.cb_chore_state)
        CheckBox checkBox;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}