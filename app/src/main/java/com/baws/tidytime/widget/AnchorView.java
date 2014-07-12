package com.baws.tidytime.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.baws.tidytime.R;

/**
 * Created by wadereweti on 11/07/14.
 */
public class AnchorView extends View {

    private final float mRelativeHeight;
    private final float mRelativeWidth;

    public AnchorView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AnchorView);
        mRelativeWidth = clamp(a.getFloat(R.styleable.AnchorView_relativeWidth, 1));
        mRelativeHeight = clamp(a.getFloat(R.styleable.AnchorView_relativeHeight, 1));
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int) (mRelativeWidth * getMeasuredWidth()), (int) (mRelativeHeight * getMeasuredHeight()));
    }

    private float clamp(float value) {
        if (value > 1) {return 1;}
        if (value < 0) {return 0;}

        return value;
    }
}
