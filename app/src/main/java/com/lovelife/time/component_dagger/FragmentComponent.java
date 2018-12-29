package com.lovelife.time.component_dagger;

import android.app.Activity;
import android.content.Context;


import com.lovelife.time.module_dagger.FragmentModule;
import com.lovelife.time.scope_dagger.ContextLife;
import com.lovelife.time.scope_dagger.PerFragment;

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
