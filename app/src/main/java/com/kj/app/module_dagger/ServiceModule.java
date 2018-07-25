package com.kj.app.module_dagger;

import android.app.Service;
import android.content.Context;


import com.kj.app.scope_dagger.ContextLife;
import com.kj.app.scope_dagger.PerService;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {
    private Service mService;

    public ServiceModule(Service service) {
        mService = service;
    }

    @Provides
    @PerService
    @ContextLife("Service")
    public Context ProvideServiceContext() {
        return mService;
    }
}
