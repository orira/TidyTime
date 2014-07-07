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
import android.widget.CheckBox;
import android.widget.ImageView;

import com.baws.tidytime.R;
import com.baws.tidytime.drawable.RoundedAvatarDrawable;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.model.Chore;
import com.baws.tidytime.widget.RobotoTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by wadereweti on 7/07/14.
 */
public class AssignedChoreAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private static final String TAG = "AssignedChoreAdapter";

    private final LayoutInflater mInflater;
    private final Resources mResources;
    private List<Chore> mChores = new ArrayList<Chore>();
    private Map mHeaderPositions = new HashMap();
    private final List<Child> mChildren;

    public AssignedChoreAdapter(Context context, List<Child> children) {
        mInflater = LayoutInflater.from(context);
        mResources = context.getResources();

        for (Child child : children) {
            mChores.addAll(child.chores());
        }

        mChildren = children;
        setupHeaderPositions();
    }

    private void setupHeaderPositions() {

        int previousPosition = 0;
        for (int i = 0; i < mChildren.size(); i++) {
            Child currentChild = mChildren.get(i);

            if (i == 0) {
                mHeaderPositions.put(i, currentChild);
            } else {
                Child previousChild = mChildren.get(i - 1);
                mHeaderPositions.put(previousPosition + previousChild.chores().size(), currentChild);
                previousPosition = previousPosition + previousChild.chores().size();
            }
        }

        Log.e(TAG, "foo");
        /*mHeaderPositions.put(0, mChildren.get(0));

        int[] positions = new int[mChildren.size()];
        positions[0] = 0;

        for (int i = 1; i < mChildren.size(); i++) {
            Child previousChild = mChildren.get(i - 1);
            Child currentChild = mChildren.get(i);

            int previousPosition = positions[i - 1];
            int choreSie = currentChild.chores().size();
            positions[i] = previousPosition + currentChild.chores().size() - 1;
        }

        for (int i = 1; i < mChildren.size(); i++) {
            Child currentChild = mChildren.get(i);

            mHeaderPositions.put(positions[i], currentChild );
        }*/
    }

    @Override
    public View getHeaderView(int position, View view, ViewGroup viewGroup) {
        HeaderViewHolder headerViewHolder;

        if (view != null) {
            headerViewHolder = (HeaderViewHolder) view.getTag();
        } else {
            view = mInflater.inflate(R.layout.list_view_assigned_header, viewGroup, false);
            headerViewHolder = new HeaderViewHolder(view);
            view.setTag(headerViewHolder);
        }

        Bitmap bitmap;

        if ((mHeaderPositions.get(position) == null)) {
            bitmap = BitmapFactory.decodeResource(mResources, R.drawable.profile_tayla);
        } else {
            if (((Child) mHeaderPositions.get(position)).firstName.equals("Tayla-Paige")) {
                bitmap = BitmapFactory.decodeResource(mResources, R.drawable.profile_tayla);
            } else if (((Child) mHeaderPositions.get(position)).firstName.equals("Kauri")) {
                bitmap = BitmapFactory.decodeResource(mResources, R.drawable.profile_kauri);
            } else {
                bitmap = BitmapFactory.decodeResource(mResources, R.drawable.profile_nevaeh);
            }
        }

        RoundedAvatarDrawable roundedAvatarDrawable = new RoundedAvatarDrawable(bitmap);
        headerViewHolder.profilePicture.setImageDrawable(roundedAvatarDrawable);

        if (mHeaderPositions.get(position) == null) {
            headerViewHolder.profileName.setText(((Child) mHeaderPositions.get(0)).firstName);
            Log.e(TAG, "position is null " + position);
        } else {
            headerViewHolder.profileName.setText(((Child) mHeaderPositions.get(position)).firstName);
        }


        return view;
    }

    @Override
    public long getHeaderId(int position) {
        return mChores.get(position).child.getId();
    }

    @Override
    public int getCount() {
        int chores = 0;

        for (Child child : mChildren)         {
            chores += child.chores().size();
        }

        //chores += mChildren.size();

        return chores;
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
        @InjectView(R.id.iv_profile_picture)
        ImageView profilePicture;

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
