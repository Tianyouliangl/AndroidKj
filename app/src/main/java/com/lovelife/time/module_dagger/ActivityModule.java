package com.lovelife.time.module_dagger;

import android.app.Activity;
import android.content.Context;


import com.lovelife.time.scope_dagger.ContextLife;
import com.lovelife.time.scope_dagger.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    @ContextLife("Activity")
    public Context provideActivityContext() {
        return mActivity;
    }

    @Provides
    @PerActivity
    public Activity provideActivity() {
        return mActivity;
    }
}
