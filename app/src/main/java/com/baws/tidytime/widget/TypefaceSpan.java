package com.baws.tidytime.widget;

/**
 * Created by Raukawa on 7/24/2014.
 */

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.util.LruCache;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

import com.baws.tidytime.activity.AbstractActivity;
import com.baws.tidytime.typeface.RobotoTypeface;

import util.TypefaceUtil;


/**
 * Style a {@link android.text.Spannable} with a custom {@link Typeface}.
 *
 * @author Tristan Waddington
 */
public class TypefaceSpan extends MetricAffectingSpan {
    /** An <code>LruCache</code> for previously loaded typefaces. */
    private static LruCache<String, Typeface> sTypefaceCache =
            new LruCache<String, Typeface>(12);

    private Typeface mTypeface;

    public TypefaceSpan(AbstractActivity context, RobotoTypeface typeface) {
        mTypeface = TypefaceUtil.getFont(context, typeface);
        sTypefaceCache.put(typeface.toString(), mTypeface);
    }

    @Override
    public void updateMeasureState(TextPaint p) {
        p.setTypeface(mTypeface);

        // Note: This flag is required for proper typeface rendering
        p.setFlags(p.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        tp.setTypeface(mTypeface);

        // Note: This flag is required for proper typeface rendering
        tp.setFlags(tp.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }
}
