package com.baws.tidytime.module.annotation;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Raukawa on 7/2/2014.
 */

@Qualifier @Retention(RUNTIME)
public @interface ForActivity {
}
