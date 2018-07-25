package com.kj.app.view.home.fragment;

import android.view.View;

import com.kj.app.R;
import com.kj.app.base.BaseFragment;


public class LiveFragment extends BaseFragment {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_live;
    }

    @Override
    protected void initInjector() {
       // mFragmentComponent.inject(this);
    }

    @Override
    protected void initView(View view) {
    }

}
