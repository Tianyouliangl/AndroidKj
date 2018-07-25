package com.kj.app.component_dagger;

import android.content.Context;


import com.kj.app.module_dagger.ApplicationModule;
import com.kj.app.scope_dagger.ContextLife;
import com.kj.app.scope_dagger.PerApp;

import dagger.Component;


@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();
}