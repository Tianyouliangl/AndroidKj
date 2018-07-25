package com.kj.app.view.loginAndRegister;


import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;

import com.kj.app.R;
import com.kj.app.base.BaseActivity;
import com.kj.app.bean.HomeCarBean;
import com.kj.app.contract.LoginContract;
import com.kj.app.presenter.LoginPresenter;
import com.kj.app.view.loginAndRegister.fragment.LoginFragment;
import com.kj.app.view.loginAndRegister.fragment.RegisterFragment;
import com.kj.app.view.loginAndRegister.viewpager.MyFragmentAdapter;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;


public class LoginAndRegisterActivity extends BaseActivity<LoginPresenter> implements LoginContract.View{


    private static ViewPager mViewPager;
    private List<RxFragment> list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initInjector() {
      mActivityComponent.inject(this);
    }

    @Override
    protected void initView() {
        list=new ArrayList<>();
        list.add(new LoginFragment());
        list.add(new RegisterFragment());
        mViewPager = findViewById(R.id.mViewPager);
        mViewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(),list));
       // mPresenter.getData();
    }
    public static void setViewPagerItem(int index){
        mViewPager.setCurrentItem(index);
    }
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (mViewPager.getCurrentItem()==list.size()-1){
                setViewPagerItem(0);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }

    @Override
    public void onSuccess(HomeCarBean msg) {

    }

    @Override
    public void onError(String a) {

    }
}
