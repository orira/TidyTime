package com.baws.tidytime.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.baws.tidytime.R;
import com.baws.tidytime.drawable.RoundedAvatarDrawable;
import com.baws.tidytime.model.Child;
import com.baws.tidytime.widget.CircularImageView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by wadereweti on 9/07/14.
 */
public class ChildSelectorAdapter extends BaseAdapter {

    private List<Child> mChildren;
    private LayoutInflater mLayoutInflater;
    private Resources mResources;

    public ChildSelectorAdapter(List<Child> children, Context context) {
        mChildren = children;
        mLayoutInflater = LayoutInflater.from(context);
        mResources = context.getResources();
    }

    @Override
    public int getCount() {
        return mChildren.size();
    }

    @Override
    public Object getItem(int postion) {
        return mChildren.get(postion);
    }

    @Override
    public long getItemId(int position) {
        return mChildren.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view != null) {
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = mLayoutInflater.inflate(R.layout.gridview_child_selector, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        Child child = mChildren.get(position);
        Bitmap bitmap;

        if (child.firstName.equals("Tayla-Paige")) {
            bitmap = BitmapFactory.decodeResource(mResources, R.drawable.profile_tayla);
        } else if (child.firstName.equals("Kauri")) {
            bitmap = BitmapFactory.decodeResource(mResources, R.drawable.profile_kauri);
        } else {
            bitmap = BitmapFactory.decodeResource(mResources, R.drawable.profile_nevaeh);
        }

        viewHolder.imageView.setImageBitmap(bitmap);
        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ViewHolder viewHolder = (ViewHolder) view.getTag();

                //view.animate().scaleX(1.2f).scaleY(1.2f);
                view.animate().scaleX(1.2f).scaleY(1.2f).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder.imageView.setBorderColor(mResources.getColor(R.color.primary_accent));
                        viewHolder.imageView.addShadow(false);

                    }
                });
                        *//*new Runnable() {
                    @Override
                    public void run() {
                        viewHolder.imageView.addShadow();
                        viewHolder.imageView.setBorderColor(mResources.getColor(R.color.primary_accent));
                        viewHolder.imageView.setBorderWidth(10);
                    }
                });*//*
            }
        });*/

        return view;
    }

    public static class ViewHolder {
        @InjectView(R.id.iv_profile_picture)
        public CircularImageView imageView;

        private ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
