package com.baws.tidytime.util;

import android.content.Context;
import android.graphics.Typeface;

import com.baws.tidytime.typeface.RobotoTypeface;


/**
 * Created by Raukawa on 6/28/2014.
 */
public class TypefaceUtil {
    private static final String PREFIX = "fonts/Roboto-";
    private static final String SUFFIX = ".ttf";

    private static final String BLACK = "Italic";
    private static final String ITALIC = "Italic";
    private static final String BOLD = "Bold";
    private static final String LIGHT = "Light";
    private static final String MEDIUM = "Medium";
    private static final String REGULAR = "Regular";
    private static final String THIN = "Thin";

    public static Typeface getFont(Context context, RobotoTypeface robotoTypeFace) {
        Typeface typeface;
        switch (robotoTypeFace) {
            case THIN:
                typeface = getRobotoThin(context);
                break;
            case LIGHT:
                typeface = getRobotoLight(context);
                break;
            case REGULAR:
                typeface = getRobotoRegular(context);
                break;
            case MEDIUM:
                typeface = getRobotoMedium(context);
                break;
            case BOLD:
                typeface = getRobotoBold(context);
                break;
            default:
                typeface = getRobotoRegular(context);
                break;
        }

        return typeface;
    }

    private static Typeface getRobotoThin(Context context) {
        return Typeface.createFromAsset(context.getAssets(), PREFIX + THIN + SUFFIX);
    }

    private static Typeface getRobotoLight(Context context) {
        return Typeface.createFromAsset(context.getAssets(), PREFIX + LIGHT + SUFFIX);
    }

    private static Typeface getRobotoRegular(Context context) {
        return Typeface.createFromAsset(context.getAssets(), PREFIX + REGULAR + SUFFIX);
    }

    private static Typeface getRobotoMedium(Context context) {
        return Typeface.createFromAsset(context.getAssets(), PREFIX + MEDIUM + SUFFIX);
    }

    private static Typeface getRobotoBold(Context context) {
        return Typeface.createFromAsset(context.getAssets(), PREFIX + BOLD + SUFFIX);
    }
}