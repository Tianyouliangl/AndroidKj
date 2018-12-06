package com.kj.app.component_dagger;

import android.app.Activity;
import android.content.Context;


import com.kj.app.module_dagger.FragmentModule;
import com.kj.app.scope_dagger.ContextLife;
import com.kj.app.scope_dagger.PerFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

 //   void inject(RegisterFragment fragment);
//
//    void inject(MineFragment fragment);

}
