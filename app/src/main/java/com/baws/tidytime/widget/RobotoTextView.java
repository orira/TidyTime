package com.baws.tidytime.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.baws.tidytime.R;
import com.baws.tidytime.typeface.RobotoTypeface;

import util.TypefaceUtil;


/**
 * Created by Raukawa on 6/28/2014.
 */
public class RobotoTextView extends TextView implements RobotoWidget{
    public RobotoTextView(Context context) {
        super(context);
        initialiseTypeface(context, null);
    }

    public RobotoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialiseTypeface(context, attrs);
    }

    public RobotoTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialiseTypeface(context, attrs);
    }

    public void initialiseTypeface(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RobotoWidget);
            String typeFaceString = a.getString(R.styleable.RobotoWidget_typeface);

            if (typeFaceString != null) {
                int typeface = Integer.parseInt(typeFaceString);
                RobotoTypeface robotTypeFace = RobotoTypeface.values()[typeface];

                this.setTypeface(TypefaceUtil.getFont(context, robotTypeFace));
            } else {
                setTypeface(TypefaceUtil.getFont(context, RobotoTypeface.REGULAR));
            }
        }
    }

    public boolean isInEditMode() {
        return true;
    }
}
