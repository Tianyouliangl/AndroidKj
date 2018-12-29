package com.lovelife.time.module_dagger;

import android.content.Context;


import com.lovelife.time.app.DHApplication;
import com.lovelife.time.scope_dagger.ContextLife;
import com.lovelife.time.scope_dagger.PerApp;

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
