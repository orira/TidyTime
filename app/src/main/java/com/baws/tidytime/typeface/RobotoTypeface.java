package com.baws.tidytime.typeface;

/**
 * Created by Raukawa on 6/28/2014.
 */
public enum RobotoTypeface {
    LIGHT(0),
    THIN(1),
    REGULAR(2),
    MEDIUM(3),
    BOLD(4);

    private int typeface;

    private RobotoTypeface(int typeface) {
        this.typeface = typeface;
    }
}
