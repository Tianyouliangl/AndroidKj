package com.lovelife.time.component_dagger;

import android.content.Context;


import com.lovelife.time.module_dagger.ServiceModule;
import com.lovelife.time.scope_dagger.ContextLife;
import com.lovelife.time.scope_dagger.PerService;

import dagger.Component;

@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {
    @ContextLife("Service")
    Context getServiceContext();

    @ContextLife("Application")
    Context getApplicationContext();
}
