package com.baws.tidytime.util;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by wadereweti on 1/08/14.
 */
public class DimensionUtil {

    public static float getFloat(int resourceId, Resources resources) {
        TypedValue outValue = new TypedValue();
        resources.getValue(resourceId, outValue, true);
        return outValue.getFloat();
    }
}
