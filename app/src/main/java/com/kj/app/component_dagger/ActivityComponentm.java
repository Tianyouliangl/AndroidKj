package com.kj.app.component_dagger;

import android.app.Activity;
import android.content.Context;


import com.kj.app.module_dagger.ActivityModule;
import com.kj.app.scope_dagger.ContextLife;
import com.kj.app.scope_dagger.PerActivity;

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
}
