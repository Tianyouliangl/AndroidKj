package com.lovelife.time.component_dagger;

import android.content.Context;


import com.lovelife.time.module_dagger.ApplicationModule;
import com.lovelife.time.scope_dagger.ContextLife;
import com.lovelife.time.scope_dagger.PerApp;

import dagger.Component;


@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();
}