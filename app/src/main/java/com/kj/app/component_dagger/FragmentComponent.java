package com.kj.app.component_dagger;

import android.app.Activity;
import android.content.Context;


import com.kj.app.module_dagger.FragmentModule;
import com.kj.app.scope_dagger.ContextLife;
import com.kj.app.scope_dagger.PerFragment;
import com.kj.app.view.home.fragment.HomePageFragment;
import com.kj.app.view.home.fragment.LiveFragment;
import com.kj.app.view.loginAndRegister.fragment.LoginFragment;
import com.kj.app.view.loginAndRegister.fragment.RegisterFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(LoginFragment fragment);

 //   void inject(RegisterFragment fragment);
//
//    void inject(MineFragment fragment);

}
