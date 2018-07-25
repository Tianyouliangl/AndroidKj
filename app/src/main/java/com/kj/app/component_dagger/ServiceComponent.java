package com.kj.app.component_dagger;

import android.content.Context;


import com.kj.app.module_dagger.ServiceModule;
import com.kj.app.scope_dagger.ContextLife;
import com.kj.app.scope_dagger.PerService;

import dagger.Component;

@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {
    @ContextLife("Service")
    Context getServiceContext();

    @ContextLife("Application")
    Context getApplicationContext();
}
