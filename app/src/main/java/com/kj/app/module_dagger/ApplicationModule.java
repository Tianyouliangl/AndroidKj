package com.kj.app.module_dagger;

import android.content.Context;


import com.kj.app.app.DHApplication;
import com.kj.app.scope_dagger.ContextLife;
import com.kj.app.scope_dagger.PerApp;

import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {
    private DHApplication mApplication;

    public ApplicationModule(DHApplication application) {
        mApplication = application;
    }

    @Provides
    @PerApp
    @ContextLife("Application")
    public Context provideApplicationContext() {
        return mApplication.getApplicationContext();
    }
}
