package com.lovelife.time.weight;

import android.app.Activity;

import com.lovelife.time.R;

public class ActivityManager {

    public static void in(Activity activity){
        activity.overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
    }
    public static void out(Activity activity){
        activity.overridePendingTransition(R.anim.anim_out,R.anim.anim_in);
    }
}
