package com.lovelife.time.component_dagger;

import android.app.Activity;
import android.content.Context;


import com.lovelife.time.module_dagger.ActivityModule;
import com.lovelife.time.scope_dagger.ContextLife;
import com.lovelife.time.scope_dagger.PerActivity;
import com.lovelife.time.view.activity.MusicActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponentm {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    //void inject(MainActivity activity);
    void inject(MusicActivity musicActivity);
}
