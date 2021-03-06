package com.lovelife.time.module_dagger;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;


import com.lovelife.time.scope_dagger.ContextLife;
import com.lovelife.time.scope_dagger.PerFragment;

import dagger.Module;
import dagger.Provides;


@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @PerFragment
    @ContextLife("Activity")
    public Context provideActivityContext() {
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Activity provideActivity() {
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Fragment provideFragment() {
        return mFragment;
    }
}
