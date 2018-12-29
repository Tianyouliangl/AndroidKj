package com.lovelife.time.app;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by fucp on 2018-4-10.
 * Description :
 */

public class LoadType {
    public static final int TYPE_REFRESH_SUCCESS = 1;
    public static final int TYPE_REFRESH_ERROR = 2;
    public static final int TYPE_LOAD_MORE_SUCCESS = 3;
    public static final int TYPE_LOAD_MORE_ERROR = 4;

    @IntDef({TYPE_REFRESH_SUCCESS, TYPE_REFRESH_ERROR, TYPE_LOAD_MORE_SUCCESS, TYPE_LOAD_MORE_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface checker {
    }
}
