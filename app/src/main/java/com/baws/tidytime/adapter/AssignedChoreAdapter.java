package com.baws.tidytime.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

    private Context mContext;
    private List<Child> mChildren;
    private final LayoutInflater mInflater;
    private List<Chore> mChores = new ArrayList<Chore>();

    @Inject LruCache<String, Bitmap> mBitmapCache;

    public AssignedChoreAdapter(Context context, List<Child> children, ObjectGraph objectGraph) {
        mContext = context;
        mChildren = children;
        mInflater = LayoutInflater.from(context);
        setData();
        objectGraph.inject(this);
    }

    @Override
    public Bitmap getBitmapFromCache(String key) {
        return mBitmapCache.get(key);
    }

    public void setData() {
        mChores.clear();

        for (Child child : mChildren) {
            mChores.addAll(child.assignedChores());
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
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        // Reset view if previous delete taken place
        if (view != null) {
            viewHolder = (ViewHolder) view.getTag();
            view.setX(0);
            view.setAlpha(1);
        } else {
            view = mInflater.inflate(R.layout.list_view_assigned_body, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        viewHolder.assignedChore.setText(mChores.get(position).description);
        viewHolder.checkBox.setChecked(mChores.get(position).complete);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewHolder holder = (ViewHolder) view.getTag();
                holder.changeSelectionState(position);
            }
        });

        return view;
    }

    private void updateList() {
        setData();
        notifyDataSetChanged();
    }

     static class HeaderViewHolder {
        @InjectView(R.id.iv_profile_picture)
        CircularImageView profilePicture;

        @InjectView(R.id.tv_profile_name)
        RobotoTextView profileName;

        private HeaderViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    class ViewHolder {
        @InjectView(R.id.container_created_chore_body) RelativeLayout container;
        @InjectView(R.id.tv_assigned_chore) RobotoTextView assignedChore;
        @InjectView(R.id.cb_chore_state)CheckBox checkBox;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

        public void changeSelectionState(int position) {
            checkBox.setChecked(!checkBox.isChecked());

            runAnimation(checkBox.isChecked(), position);
        }

        private void runAnimation(boolean checked, final int position) {
            int alpha = checked ? 0 : 1;
            int width = checked ? container.getWidth() : 0;

            container.animate().translationX(width).alpha(alpha).withEndAction(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(container.getContext(), mContext.getString(R.string.toast_chore_completed), Toast.LENGTH_SHORT).show();
                    Chore chore = mChores.get(position);
                    chore.complete = true;
                    chore.save();

                    updateList();
                }
            });
        }
    }
}